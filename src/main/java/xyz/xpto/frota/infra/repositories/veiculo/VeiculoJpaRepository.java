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
import xyz.xpto.frota.domain.dtos.VeiculoDTO;
import xyz.xpto.frota.domain.entities.Veiculo;

@Repository
public interface VeiculoJpaRepository extends JpaRepository<Veiculo, Long> {
	@Transactional
	@Query(value = """
			INSERT INTO veiculo (modelo, fabricante, ano, preco, tipo)
			VALUES (:modelo, :fabricante, :ano, :preco, :tipo)
			RETURNING id
			""", nativeQuery = true)
	Integer inserir(
			@Param("modelo") String modelo,
			@Param("fabricante") String fabricante,
			@Param("ano") Integer ano,
			@Param("preco") BigDecimal preco,
			@Param("tipo") String tipo);

	@Query(value = """
	SELECT v.id, v.modelo, v.fabricante, v.ano, v.preco, v.tipo, c.quantidade_portas, c.tipo_combustivel, m.cilindrada
        FROM veiculo v
        LEFT JOIN carro c ON c.veiculo_id = v.id
        LEFT JOIN moto m ON m.veiculo_id = v.id 
	WHERE v.id = :id
	""", nativeQuery = true)
	Optional<VeiculoDTO> buscarPorId(Long id);

	@Query(value = """
        SELECT v.id, v.modelo, v.fabricante, v.ano, v.preco, v.tipo, c.quantidade_portas, c.tipo_combustivel, m.cilindrada
        FROM veiculo v
        LEFT JOIN carro c ON c.veiculo_id = v.id
        LEFT JOIN moto m ON m.veiculo_id = v.id
        """,
        nativeQuery = true)
	List<VeiculoDTO> buscarTodos();

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
