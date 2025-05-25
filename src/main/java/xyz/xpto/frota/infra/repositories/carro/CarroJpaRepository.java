package xyz.xpto.frota.infra.repositories.carro;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import xyz.xpto.frota.domain.entities.Carro;

@Repository
public interface CarroJpaRepository extends JpaRepository<Carro, Long> {
	@Transactional
	@Query(value = """
			INSERT INTO carro (veiculo_id, quantidade_portas, tipo_combustivel)
			VALUES (:veiculoId, :quantidadePortas, :tipoCombustivel)
			RETURNING veiculo_id
			""", nativeQuery = true)
	Integer inserir(
			@Param("veiculoId") Long veiculoId,
			@Param("quantidadePortas") Integer quantidadePortas,
			@Param("tipoCombustivel") String tipoCombustivel);

	@Query(value = """
			SELECT * FROM carro c
			WHERE c.veiculo_id = :veiculoId
			""", nativeQuery = true)
	Optional<Carro> buscarPorId(Long veiculoId);

	@Query(value = "SELECT * FROM carro c", nativeQuery = true)
	List<Carro> buscarTodos();

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM carro c WHERE c.veiculo_id = :veiculoId", nativeQuery = true)
	void deletar(Long veiculoId);

	@Modifying
	@Transactional
	@Query(value = """
			UPDATE carro
			SET quantidade_portas = :quantidadePortas,
			    tipo_combustivel = :tipoCombustivel
			WHERE veiculo_id = :veiculoId
			""", nativeQuery = true)
	int atualizar(
			@Param("veiculoId") Long veiculoId,
			@Param("quantidadePortas") Integer quantidadePortas,
			@Param("tipoCombustivel") String tipoCombustivel);
}
