package xyz.xpto.frota.infra.repositories.moto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import xyz.xpto.frota.domain.entities.Moto;

@Repository
public interface MotoJpaRepository extends JpaRepository<Moto, Long> {
	@Transactional
	@Query(value = """
			INSERT INTO moto (veiculo_id, cilindrada)
			VALUES (:veiculoId, :cilindrada)
			RETURNING veiculo_id
			""", nativeQuery = true)
	Integer inserir(
			@Param("veiculoId") Long veiculoId,
			@Param("cilindrada") Integer cilindrada);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM moto m WHERE m.veiculo_id = :idVeiculo", nativeQuery = true)
	void deletar(Long idVeiculo);

	@Modifying
	@Transactional
	@Query(value = """
			UPDATE moto
			SET cilindrada = :cilindrada
			WHERE veiculo_id = :idVeiculo
			""", nativeQuery = true)
	int atualizar(
			@Param("idVeiculo") Long idVeiculo,
			@Param("cilindrada") Integer cilindrada);

}
