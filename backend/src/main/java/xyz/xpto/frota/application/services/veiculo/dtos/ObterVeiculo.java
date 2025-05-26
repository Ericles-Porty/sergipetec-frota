package xyz.xpto.frota.application.services.veiculo.dtos;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

public class ObterVeiculo {

	@Data
	@Builder
	public static class Request {
		Long id;
	}

	@Data
	@Builder
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Response {
		Long id;
		String modelo;
		String fabricante;
		Integer ano;
		BigDecimal preco;
		String cor;
		String tipo;
		Integer quantidadePortas;
		String tipoCombustivel;
		Integer cilindrada;
	}
}
