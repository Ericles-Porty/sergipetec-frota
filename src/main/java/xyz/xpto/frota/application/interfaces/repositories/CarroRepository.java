package xyz.xpto.frota.application.interfaces.repositories;

import java.util.List;
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
	 * Busca um carro pelo ID.
	 *
	 * @param id o ID do carro a ser buscado
	 * @return o carro encontrado, se existir; caso contrário, Optional.empty()
	 */
	Optional<Carro> buscarPorId(Long id);

	/**
	 * Busca todos os carros no repositório.
	 *
	 * @return uma lista de todos os carros
	 */
	List<Carro> buscarTodos();

	/**
	 * Deleta um carro pelo ID.
	 *
	 * @param id o ID do carro a ser deletado
	 */
	void deletar(Long id);

}