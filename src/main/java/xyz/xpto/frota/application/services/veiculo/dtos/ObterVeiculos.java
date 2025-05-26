package xyz.xpto.frota.application.services.veiculo.dtos;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

public class ObterVeiculos {

	@Data
	@Builder
	public static class Request {
		String tipo;
		String modelo;
		String cor;
		Integer ano;
	}

	@Data
	@Builder
	public static class Response {
		List<VeiculoResponse> veiculos;

		@Data
		@Builder
		@JsonInclude(JsonInclude.Include.NON_NULL)
		public static class VeiculoResponse {
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

}
