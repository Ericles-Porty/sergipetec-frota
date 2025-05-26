package xyz.xpto.frota.api.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import xyz.xpto.frota.api.extensions.StandardResponse;
import xyz.xpto.frota.api.extensions.ValidationExtension;
import xyz.xpto.frota.application.services.veiculo.VeiculoService;
import xyz.xpto.frota.application.services.veiculo.dtos.AtualizarVeiculo;
import xyz.xpto.frota.application.services.veiculo.dtos.CadastrarVeiculo;
import xyz.xpto.frota.application.services.veiculo.dtos.DeletarVeiculo;
import xyz.xpto.frota.application.services.veiculo.dtos.ObterVeiculo;
import xyz.xpto.frota.application.services.veiculo.dtos.ObterVeiculos;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/veiculos", produces = MediaType.APPLICATION_JSON_VALUE)
public class VeiculoController {

	private final VeiculoService veiculoService;

	@GetMapping(value = "")
	public ResponseEntity<?> getVeiculos(
			@RequestParam(required = false) String tipo,
			@RequestParam(required = false) String modelo,
			@RequestParam(required = false) String cor,
			@RequestParam(required = false) Integer ano) {

		ObterVeiculos.Request request = ObterVeiculos.Request.builder()
				.tipo(tipo)
				.modelo(modelo)
				.cor(cor)
				.ano(ano)
				.build();

		ObterVeiculos.Response response = veiculoService.obterVeiculos(request);
		return StandardResponse.success(response.getVeiculos());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getVeiculo(
			@PathVariable Long id) {
		ObterVeiculo.Response response = veiculoService.obterVeiculo(ObterVeiculo.Request.builder().id(id).build());
		return StandardResponse.success(response);
	}

	@PostMapping(value = "")
	public ResponseEntity<?> postVeiculo(@RequestBody @Valid CadastrarVeiculo.Request request,
			BindingResult bindingResult) {
		ValidationExtension.validateBindingResult(bindingResult);

		var response = veiculoService.cadastrarVeiculo(request);
		return StandardResponse.created(response);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> putVeiculo(@PathVariable Long id, @RequestBody @Valid AtualizarVeiculo.Request request,
			BindingResult bindingResult) {
		ValidationExtension.validateBindingResult(bindingResult);
		if (id != request.getId())
			return StandardResponse
					.badRequest("O id informado na URL é diferente do id informado no corpo da requisição.");

		var response = veiculoService.atualizarVeiculo(request);
		return StandardResponse.success(response);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteVeiculo(@PathVariable Long id) {
		var response = veiculoService.deletarVeiculo(DeletarVeiculo.Request.builder().id(id).build());
		return StandardResponse.success(response);
	}
}
