package xyz.xpto.frota.application.interfaces.repositories;

import java.util.List;
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
	 * Busca uma moto pelo ID.
	 *
	 * @param id o ID da moto a ser buscada
	 * @return a moto encontrada, se existir; caso contrário, Optional.empty()
	 */
	Optional<Moto> buscarPorId(Long id);

	/**
	 * Busca todos as motos no repositório.
	 *
	 * @return uma lista de todos as motos
	 */
	List<Moto> buscarTodos();

	/**
	 * Deleta uma moto pelo ID.
	 *
	 * @param id o ID da moto a ser deletada
	 */
	void deletar(Long id);

}