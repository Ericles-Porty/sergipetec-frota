package xyz.xpto.frota.infra.repositories.veiculo;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import xyz.xpto.frota.application.interfaces.repositories.VeiculoRepository;
import xyz.xpto.frota.domain.entities.Veiculo;

@Repository
public class VeiculoRepositoryImpl implements VeiculoRepository {

	private final VeiculoJpaRepository veiculoJpaRepository;

	public VeiculoRepositoryImpl(VeiculoJpaRepository veiculoJpaRepository) {
		this.veiculoJpaRepository = veiculoJpaRepository;
	}

	@Override
	public Veiculo salvar(Veiculo veiculo) {
		Long idVeiculoCriado = veiculoJpaRepository.inserir(
				veiculo.getModelo(),
				veiculo.getFabricante(),
				veiculo.getAno(),
				veiculo.getPreco());

		return veiculoJpaRepository.findById(idVeiculoCriado)
				.orElseThrow(() -> new RuntimeException("Erro ao inserir veículo"));
	}

	@Override
	public Optional<Veiculo> atualizar(Veiculo veiculo) {
		int linhasAfetadas = veiculoJpaRepository.atualizar(
				veiculo.getId(),
				veiculo.getModelo(),
				veiculo.getFabricante(),
				veiculo.getAno(),
				veiculo.getPreco());

		if (linhasAfetadas > 0) {
			return veiculoJpaRepository.buscarPorId(veiculo.getId());
		}

		return Optional.empty();
	}

	@Override
	public Optional<Veiculo> buscarPorId(Long id) {
		return veiculoJpaRepository.buscarPorId(id);
	}

	@Override
	public List<Veiculo> buscarTodos() {
		return veiculoJpaRepository.buscarTodos();
	}

	@Override
	public void deletar(Long id) {
		veiculoJpaRepository.deletar(id);
	}

}
