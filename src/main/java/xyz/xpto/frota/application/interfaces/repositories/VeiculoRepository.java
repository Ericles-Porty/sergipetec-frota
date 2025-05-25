package xyz.xpto.frota.application.interfaces.repositories;

import java.util.List;
import java.util.Optional;

import xyz.xpto.frota.domain.dtos.VeiculoDTO;
import xyz.xpto.frota.domain.entities.Veiculo;

public interface VeiculoRepository {

	/**
	 * Salva um veículo no repositório.
	 *
	 * @param veiculo o veículo a ser salvo
	 * @return o veículo salvo
	 */
	VeiculoDTO salvar(Veiculo veiculo);

	/**
	 * Atualiza um veículo no repositório.
	 *
	 * @param veiculo o veículo a ser atualizado
	 * @return o veículo atualizado, se encontrado; caso contrário, Optional.empty()
	 */
	Optional<VeiculoDTO> atualizar(Veiculo veiculo);

	/**
	 * Busca um veículo pelo ID.
	 *
	 * @param id o ID do veículo a ser buscado
	 * @return o veículo encontrado, se existir; caso contrário, Optional.empty()
	 */
	Optional<VeiculoDTO> buscarPorId(Long id);

	/**
	 * Busca todos os veículos no repositório.
	 *
	 * @return uma lista de todos os veículos
	 */
	List<VeiculoDTO> buscarTodos();

	/**
	 * Deleta um veículo pelo ID.
	 *
	 * @param id o ID do veículo a ser deletado
	 */
	void deletar(Long id);

}