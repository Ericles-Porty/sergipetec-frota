package xyz.xpto.frota.application.interfaces.repositories;

import java.util.Optional;

import xyz.xpto.frota.domain.entities.Moto;

public interface MotoRepository {

	/**
	 * Salva uma moto no repositório.
	 *
	 * @param moto a moto a ser salva
	 * @return a moto salva
	 */
	Moto salvar(Moto moto);

	/**
	 * Atualiza uma moto no repositório.
	 *
	 * @param moto a moto a ser atualizada
	 * @return a moto atualizada, se encontrado; caso contrário, Optional.empty()
	 */
	Optional<Moto> atualizar(Moto moto);

	/**
	 * Deleta uma moto pelo ID.
	 *
	 * @param veiculoId o ID da moto a ser deletada
	 */
	void deletar(Long veiculoId);

}