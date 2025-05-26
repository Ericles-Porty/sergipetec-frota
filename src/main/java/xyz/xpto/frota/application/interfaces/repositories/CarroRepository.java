package xyz.xpto.frota.application.interfaces.repositories;

import java.util.Optional;

import xyz.xpto.frota.domain.entities.Carro;

public interface CarroRepository {

	/**
	 * Salva um carro no repositório.
	 *
	 * @param carro o carro a ser salvo
	 * @return o carro salvo
	 */
	Carro salvar(Carro carro);

	/**
	 * Atualiza um carro no repositório.
	 *
	 * @param carro o carro a ser atualizado
	 * @return o carro atualizado, se encontrado; caso contrário, Optional.empty()
	 */
	Optional<Carro> atualizar(Carro carro);

	/**
	 * Deleta um carro pelo ID.
	 *
	 * @param id o ID do carro a ser deletado
	 */
	void deletar(Long id);

}