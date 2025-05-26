package xyz.xpto.frota.infra.repositories.carro;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import xyz.xpto.frota.application.interfaces.repositories.CarroRepository;
import xyz.xpto.frota.domain.entities.Carro;

@Repository
public class CarroRepositoryImpl implements CarroRepository {

	private final CarroJpaRepository carroJpaRepository;

	public CarroRepositoryImpl(CarroJpaRepository carroJpaRepository) {
		this.carroJpaRepository = carroJpaRepository;
	}

	@Override
	public Carro salvar(Carro carro) {
		Integer idCarroCriado = carroJpaRepository.inserir(
				carro.getId(),
				carro.getQuantidadePortas(),
				carro.getTipoCombustivel().name());

		return carroJpaRepository.findById(idCarroCriado.longValue())
				.orElseThrow(() -> new RuntimeException("Erro ao inserir carro"));
	}

	@Override
	public Optional<Carro> atualizar(Carro carro) {
		int linhasAfetadas = carroJpaRepository.atualizar(
				carro.getId(),
				carro.getQuantidadePortas(),
				carro.getTipoCombustivel().name());

		if (linhasAfetadas > 0) {
			return Optional.of(carro);
		}

		return Optional.empty();
	}

	@Override
	public void deletar(Long idVeiculo) {
		carroJpaRepository.deletar(idVeiculo);
	}

}
