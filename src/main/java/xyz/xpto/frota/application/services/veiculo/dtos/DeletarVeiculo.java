package xyz.xpto.frota.application.services.veiculo.dtos;

import lombok.Builder;
import lombok.Data;

public class DeletarVeiculo {

	@Data
	@Builder
	public static class Request {
		private Long id;
	}

	public static class Response {
	}
}
