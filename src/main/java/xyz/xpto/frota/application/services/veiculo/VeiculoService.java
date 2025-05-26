package xyz.xpto.frota.application.services.veiculo;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import xyz.xpto.frota.application.interfaces.repositories.CarroRepository;
import xyz.xpto.frota.application.interfaces.repositories.MotoRepository;
import xyz.xpto.frota.application.interfaces.repositories.VeiculoRepository;
import xyz.xpto.frota.application.services.veiculo.dtos.AtualizarVeiculo;
import xyz.xpto.frota.application.services.veiculo.dtos.CadastrarVeiculo;
import xyz.xpto.frota.application.services.veiculo.dtos.DeletarVeiculo;
import xyz.xpto.frota.application.services.veiculo.dtos.ObterVeiculo;
import xyz.xpto.frota.application.services.veiculo.dtos.ObterVeiculos;
import xyz.xpto.frota.domain.dtos.VeiculoDTO;
import xyz.xpto.frota.domain.entities.Carro;
import xyz.xpto.frota.domain.entities.Moto;
import xyz.xpto.frota.domain.entities.Carro.TipoCombustivel;

@Service
@RequiredArgsConstructor
public class VeiculoService {

	private final VeiculoRepository veiculoRepository;
	private final CarroRepository carroRepository;
	private final MotoRepository motoRepository;

	public ObterVeiculos.Response obterVeiculos(ObterVeiculos.Request request) {
		List<VeiculoDTO> veiculos = veiculoRepository.buscarTodos();
		List<ObterVeiculos.Response.VeiculoResponse> veiculosResponse = veiculos.stream()
				.map(veiculo -> ObterVeiculos.Response.VeiculoResponse.builder()
						.id(veiculo.getId())
						.modelo(veiculo.getModelo())
						.fabricante(veiculo.getFabricante())
						.ano(veiculo.getAno())
						.preco(veiculo.getPreco())
						.cor(veiculo.getCor())
						.tipo(veiculo.getTipo())
						.quantidadePortas(veiculo.getQuantidadePortas())
						.tipoCombustivel(veiculo.getTipoCombustivel())
						.cilindrada(veiculo.getCilindrada())
						.build())
				.toList();

		return ObterVeiculos.Response.builder().veiculos(veiculosResponse).build();
	}

	public ObterVeiculo.Response obterVeiculo(ObterVeiculo.Request request) {
		var veiculo = veiculoRepository.buscarPorId(request.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Veiculo não encontrado"));

		ObterVeiculo.Response response = ObterVeiculo.Response.builder()
				.id(veiculo.getId())
				.modelo(veiculo.getModelo())
				.fabricante(veiculo.getFabricante())
				.ano(veiculo.getAno())
				.preco(veiculo.getPreco())
				.cor(veiculo.getCor())
				.tipo(veiculo.getTipo())
				.quantidadePortas(veiculo.getQuantidadePortas())
				.tipoCombustivel(veiculo.getTipoCombustivel())
				.cilindrada(veiculo.getCilindrada())
				.build();

		return response;
	}

	public CadastrarVeiculo.Response cadastrarVeiculo(CadastrarVeiculo.Request request) {
		if (request.getTipo().equalsIgnoreCase("carro")) {
			var carro = Carro.builder()
					.modelo(request.getModelo())
					.fabricante(request.getFabricante())
					.ano(request.getAno())
					.preco(request.getPreco())
					.cor(request.getCor())
					.tipo(request.getTipo())
					.quantidadePortas(request.getQuantidadePortas())
					.tipoCombustivel(TipoCombustivel.valueOf(request.getTipoCombustivel())).build();

			VeiculoDTO veiculoCriado = veiculoRepository.salvar(carro);
			System.out.println("Veiculo criado: " + veiculoCriado);
			System.out.println(veiculoCriado.getId());
			carro.setId(veiculoCriado.getId());
			var carroCriado = carroRepository.salvar(carro);

			var response = CadastrarVeiculo.Response.builder()
					.id(carroCriado.getId())
					.modelo(carroCriado.getModelo())
					.fabricante(carroCriado.getFabricante())
					.ano(carroCriado.getAno())
					.preco(carroCriado.getPreco())
					.cor(carroCriado.getCor())
					.tipo(carroCriado.getTipo())
					.quantidadePortas(carroCriado.getQuantidadePortas())
					.tipoCombustivel(carroCriado.getTipoCombustivel().name())
					.build();

			return response;
		} else if (request.getTipo().equalsIgnoreCase("moto")) {
			var moto = Moto.builder()
					.modelo(request.getModelo())
					.fabricante(request.getFabricante())
					.ano(request.getAno())
					.preco(request.getPreco())
					.cor(request.getCor())
					.tipo(request.getTipo())
					.cilindrada(request.getCilindrada())
					.build();

			var veiculoCriado = veiculoRepository.salvar(moto);
			moto.setId(veiculoCriado.getId());
			var motoCriada = motoRepository.salvar(moto);

			var response = CadastrarVeiculo.Response.builder()
					.id(motoCriada.getId())
					.modelo(motoCriada.getModelo())
					.fabricante(motoCriada.getFabricante())
					.ano(motoCriada.getAno())
					.preco(motoCriada.getPreco())
					.cor(motoCriada.getCor())
					.tipo(motoCriada.getTipo())
					.cilindrada(motoCriada.getCilindrada())
					.build();

			return response;
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de veículo inválido");
		}
	}

	public AtualizarVeiculo.Response atualizarVeiculo(AtualizarVeiculo.Request request) {
		VeiculoDTO veiculoDto = veiculoRepository.buscarPorId(request.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado"));

		List<String> erros = List.of();

		if (veiculoDto.getTipo() == "CARRO" && request.getCilindrada() != null) {
			erros.add("Cilindrada não pode ser informada para carro");
		} else if (veiculoDto.getTipo() == "MOTO") {
			if (request.getQuantidadePortas() != null) {
				erros.add("Quantidade de portas não pode ser informada para moto");
			}
			if (request.getTipoCombustivel() != null) {
				erros.add("Tipo de combustível não pode ser informado para moto");
			}
		}

		if (!erros.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.join(", ", erros));
		}

		Optional.ofNullable(request.getModelo()).ifPresent(veiculoDto::setModelo);
		Optional.ofNullable(request.getFabricante()).ifPresent(veiculoDto::setFabricante);
		Optional.ofNullable(request.getAno()).ifPresent(veiculoDto::setAno);
		Optional.ofNullable(request.getPreco()).ifPresent(veiculoDto::setPreco);
		Optional.ofNullable(request.getCor()).ifPresent(veiculoDto::setCor);
		Optional.ofNullable(request.getQuantidadePortas()).ifPresent(veiculoDto::setQuantidadePortas);
		Optional.ofNullable(request.getTipoCombustivel()).ifPresent(veiculoDto::setTipoCombustivel);
		Optional.ofNullable(request.getCilindrada()).ifPresent(veiculoDto::setCilindrada);

		if (veiculoDto.getTipo().equalsIgnoreCase("CARRO")) {
			Carro carro = Carro.builder()
					.id(veiculoDto.getId())
					.modelo(veiculoDto.getModelo())
					.fabricante(veiculoDto.getFabricante())
					.ano(veiculoDto.getAno())
					.preco(veiculoDto.getPreco())
					.cor(veiculoDto.getCor())
					.tipo(veiculoDto.getTipo())
					.quantidadePortas(veiculoDto.getQuantidadePortas())
					.tipoCombustivel(TipoCombustivel.valueOf(veiculoDto.getTipoCombustivel()))
					.build();
			veiculoRepository.atualizar(carro);
			carroRepository.atualizar(carro);

		} else if (veiculoDto.getTipo().equalsIgnoreCase("MOTO")) {
			Moto moto = Moto.builder()
					.id(veiculoDto.getId())
					.modelo(veiculoDto.getModelo())
					.fabricante(veiculoDto.getFabricante())
					.ano(veiculoDto.getAno())
					.preco(veiculoDto.getPreco())
					.cor(veiculoDto.getCor())
					.tipo(veiculoDto.getTipo())
					.cilindrada(veiculoDto.getCilindrada())
					.build();
			veiculoRepository.atualizar(moto);
			motoRepository.atualizar(moto);
		} else {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Tipo de veículo inválido");
		}

		AtualizarVeiculo.Response response = AtualizarVeiculo.Response.builder()
				.id(veiculoDto.getId())
				.modelo(veiculoDto.getModelo())
				.fabricante(veiculoDto.getFabricante())
				.ano(veiculoDto.getAno())
				.preco(veiculoDto.getPreco())
				.cor(veiculoDto.getCor())
				.tipo(veiculoDto.getTipo())
				.quantidadePortas(veiculoDto.getQuantidadePortas())
				.tipoCombustivel(veiculoDto.getTipoCombustivel())
				.cilindrada(veiculoDto.getCilindrada())
				.build();

		return response;
	}

	public DeletarVeiculo.Response deletarVeiculo(DeletarVeiculo.Request request) {
		VeiculoDTO veiculo = veiculoRepository.buscarPorId(request.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado"));

		veiculoRepository.deletar(veiculo.getId());
		return new DeletarVeiculo.Response();
	}
}
