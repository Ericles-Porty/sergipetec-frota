package xyz.xpto.frota.infra.repositories.moto;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import xyz.xpto.frota.application.interfaces.repositories.MotoRepository;
import xyz.xpto.frota.domain.entities.Moto;

@Repository
public class MotoRepositoryImpl implements MotoRepository {

	private final MotoJpaRepository motoJpaRepository;

	public MotoRepositoryImpl(MotoJpaRepository motoJpaRepository) {
		this.motoJpaRepository = motoJpaRepository;
	}

	@Override
	public Moto salvar(Moto moto) {
		Integer idMotoCriado = motoJpaRepository.inserir(moto.getId(), moto.getCilindrada());

		return motoJpaRepository.findById(idMotoCriado.longValue())
				.orElseThrow(() -> new RuntimeException("Erro ao inserir moto"));
	}

	@Override
	public Optional<Moto> atualizar(Moto moto) {
		int linhasAfetadas = motoJpaRepository.atualizar(
				moto.getId(),
				moto.getCilindrada());

		if (linhasAfetadas > 0) {
			return Optional.of(moto);
		}

		return Optional.empty();
	}

	@Override
	public Optional<Moto> buscarPorId(Long idVeiculo) {
		return motoJpaRepository.buscarPorId(idVeiculo);
	}

	@Override
	public List<Moto> buscarTodos() {
		return motoJpaRepository.buscarTodos();
	}

	@Override
	public void deletar(Long idVeiculo) {
		motoJpaRepository.deletar(idVeiculo);
	}

}
