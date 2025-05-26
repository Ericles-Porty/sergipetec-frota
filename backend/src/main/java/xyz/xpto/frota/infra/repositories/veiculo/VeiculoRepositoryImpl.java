package xyz.xpto.frota.infra.repositories.veiculo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import xyz.xpto.frota.application.interfaces.repositories.VeiculoRepository;
import xyz.xpto.frota.domain.dtos.VeiculoDTO;
import xyz.xpto.frota.domain.entities.Veiculo;

@Repository
public class VeiculoRepositoryImpl implements VeiculoRepository {

	@PersistenceContext
	private EntityManager em;

	private final VeiculoJpaRepository veiculoJpaRepository;

	public VeiculoRepositoryImpl(VeiculoJpaRepository veiculoJpaRepository) {
		this.veiculoJpaRepository = veiculoJpaRepository;
	}

	@Override
	public VeiculoDTO salvar(Veiculo veiculo) {
		Integer veiculoIdCriado = veiculoJpaRepository.inserir(
				veiculo.getModelo(),
				veiculo.getFabricante(),
				veiculo.getAno(),
				veiculo.getPreco(),
				veiculo.getCor(),
				veiculo.getTipo());
		System.out.println("ID do veículo criado: " + veiculoIdCriado);

		return veiculoJpaRepository.buscarPorId(veiculoIdCriado.longValue())
				.orElseThrow(() -> new RuntimeException("Erro ao inserir veículo"));
	}

	@Override
	public Optional<VeiculoDTO> atualizar(Veiculo veiculo) {
		int linhasAfetadas = veiculoJpaRepository.atualizar(
				veiculo.getId(),
				veiculo.getModelo(),
				veiculo.getFabricante(),
				veiculo.getAno(),
				veiculo.getPreco(),
				veiculo.getCor());

		if (linhasAfetadas > 0) {
			return veiculoJpaRepository.buscarPorId(veiculo.getId());
		}

		return Optional.empty();
	}

	@Override
	public Optional<VeiculoDTO> buscarPorId(Long id) {
		return veiculoJpaRepository.buscarPorId(id);
	}

	@Override
	public List<VeiculoDTO> buscarTodos(String tipo, String modelo, String cor, Integer ano) {
		StringBuilder sql = new StringBuilder(
				"""
						SELECT
						    v.id,
						    v.modelo,
						    v.fabricante,
						    v.ano,
						    v.preco,
						    v.cor,
						    v.tipo,
						    c.quantidade_portas,
						    c.tipo_combustivel,
						    m.cilindrada
						FROM veiculo v
						LEFT JOIN carro c ON c.veiculo_id = v.id
						LEFT JOIN moto m ON m.veiculo_id = v.id
						WHERE 1=1
						""");

		if (tipo != null)
			sql.append(" AND v.tipo = :tipo");
		if (modelo != null)
			sql.append(" AND v.modelo ILIKE :modelo");
		if (cor != null)
			sql.append(" AND v.cor = :cor");
		if (ano != null)
			sql.append(" AND v.ano = :ano");

		Query query = em.createNativeQuery(sql.toString());

		if (tipo != null)
			query.setParameter("tipo", tipo);
		if (modelo != null)
			query.setParameter("modelo", "%" + modelo + "%");
		if (cor != null)
			query.setParameter("cor", cor);
		if (ano != null)
			query.setParameter("ano", ano);

		@SuppressWarnings("unchecked")
		List<Object[]> results = (List<Object[]>) query.getResultList();

		return results.stream().map(row -> new VeiculoDTO(
				((Number) row[0]).longValue(), // id
				(String) row[1], // modelo
				(String) row[2], // fabricante
				(Integer) row[3], // ano
				(BigDecimal) row[4], // preco
				(String) row[5], // cor
				(String) row[6], // tipo
				row[7] != null ? ((Number) row[7]).intValue() : null, // quantidade_portas
				(String) row[8], // tipo_combustivel
				row[9] != null ? ((Number) row[9]).intValue() : null // cilindrada
		)).toList();
	}

	@Override
	public void deletar(Long id) {
		veiculoJpaRepository.deletar(id);
	}

}
