package xyz.xpto.frota.infra.repositories.veiculo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import xyz.xpto.frota.domain.entities.Veiculo;

@Repository
public interface VeiculoJpaRepository extends JpaRepository<Veiculo, Long> {
	@Modifying
	@Transactional
	@Query(value = """
			INSERT INTO veiculo (modelo, fabricante, ano, preco)
			VALUES (:modelo, :fabricante, :ano, :preco)
			RETURNING id
			""", nativeQuery = true)
	Long inserir(
			@Param("modelo") String modelo,
			@Param("fabricante") String fabricante,
			@Param("ano") Integer ano,
			@Param("preco") BigDecimal preco);

	@Query(value = "SELECT * FROM veiculo t WHERE t.id = :id", nativeQuery = true)
	Optional<Veiculo> buscarPorId(Long id);

	@Query(value = "SELECT * FROM veiculo t", nativeQuery = true)
	List<Veiculo> buscarTodos();

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM veiculo t WHERE t.id = :id", nativeQuery = true)
	void deletar(Long id);

	@Modifying
	@Transactional
	@Query(value = """
			UPDATE veiculo SET
				modelo = :modelo,
				fabricante = :fabricante,
				ano = :ano,
				preco = :preco
			WHERE id = :id
			""", nativeQuery = true)
	int atualizar(
			@Param("id") Long id,
			@Param("modelo") String modelo,
			@Param("fabricante") String fabricante,
			@Param("ano") Integer ano,
			@Param("preco") BigDecimal preco);
}
