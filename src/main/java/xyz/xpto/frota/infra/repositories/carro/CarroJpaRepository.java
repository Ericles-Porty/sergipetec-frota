package xyz.xpto.frota.infra.repositories.carro;

import java.math.BigDecimal;
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
	@Modifying
	@Transactional
	@Query(value = """
			INSERT INTO carro (quantidade_portas, tipo_combustivel)
			VALUES (:quantidadePortas, :tipoCombustivel)
			RETURNING id_veiculo
			""", nativeQuery = true)
	Long inserir(
			@Param("quantidadePortas") Integer quantidadePortas,
			@Param("tipoCombustivel") String tipoCombustivel);

	@Query(value = "SELECT * FROM carro c WHERE c.id_veiculo = :idVeiculo", nativeQuery = true)
	Optional<Carro> buscarPorId(Long idVeiculo);

	@Query(value = "SELECT * FROM carro c", nativeQuery = true)
	List<Carro> buscarTodos();

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM carro c WHERE c.id_veiculo = :idVeiculo", nativeQuery = true)
	void deletar(Long idVeiculo);

	@Modifying
	@Transactional
	@Query(value = """
			UPDATE carro
			SET quantidade_portas = :quantidadePortas,
			    tipo_combustivel = :tipoCombustivel
			WHERE veiculo_id = :idVeiculo
			""", nativeQuery = true)
	int atualizar(
			@Param("idVeiculo") Long idVeiculo,
			@Param("quantidadePortas") Integer quantidadePortas,
			@Param("tipoCombustivel") String tipoCombustivel);
}
