package xyz.xpto.frota.application.services.veiculo.dtos;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

public class CadastrarVeiculo {

	@Data
	@Builder
	public static class Request {
		@NotBlank(message = "Modelo é obrigatório")
		@Size(min = 3, max = 100, message = "Modelo deve ter entre 3 e 100 caracteres")
		String modelo;

		@NotBlank(message = "Fabricante é obrigatória")
		@Length(min = 3, max = 100, message = "Fabricante deve ter entre 3 e 100 caracteres")
		String fabricante;

		@NotNull(message = "Ano é obrigatório")
		@Min(value = 1886, message = "Ano deve ser maior ou igual a 1886")
		@Max(value = 2026, message = "Ano deve ser menor ou igual a 2026")
		Integer ano;

		@NotNull(message = "Preço é obrigatório")
		@DecimalMin(value = "0.00", inclusive = true, message = "Preço deve ser positivo")
		BigDecimal preco;

		@NotBlank(message = "Tipo de veículo é obrigatório (carro ou moto)")
		@Pattern(regexp = "CARRO|MOTO", message = "Tipo de veículo inválido")
		String tipo;

		@NotBlank(message = "Cor é obrigatória")
		@Size(min = 3, max = 50, message = "Cor deve ter entre 3 e 50 caracteres")
		String cor;

		// Apenas para carro
		Integer quantidadePortas;
		@Pattern(regexp = "GASOLINA|ETANOL|DIESEL|FLEX", message = "Tipo de combustível inválido")
		String tipoCombustivel;

		// Apenas para moto
		Integer cilindrada;
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
