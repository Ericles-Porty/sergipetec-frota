// src/main/java/xyz/xpto/frota/application/dtos/VeiculoDTO.java
package xyz.xpto.frota.domain.dtos;

import java.math.BigDecimal;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class VeiculoDTO {
	private Long id;
	private String modelo;
	private String fabricante;
	private Integer ano;
	private BigDecimal preco;
	private String tipo;
	private Integer quantidadePortas;
	private String tipoCombustivel;
	private Integer cilindrada;

	public VeiculoDTO(Long id, String modelo, String fabricante, Integer ano, BigDecimal preco, String tipo,
			Integer quantidadePortas, String tipoCombustivel, Integer cilindrada) {
		this.id = id;
		this.modelo = modelo;
		this.fabricante = fabricante;
		this.ano = ano;
		this.preco = preco;
		this.tipo = tipo;
		this.quantidadePortas = quantidadePortas;
		this.tipoCombustivel = tipoCombustivel;
		this.cilindrada = cilindrada;
	}

}
