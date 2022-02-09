package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.AvaliacaoDTO;
import br.com.meliw4.projetointegrador.dto.AvaliacaoUpdateDTO;
import br.com.meliw4.projetointegrador.entity.Avaliacao;
import br.com.meliw4.projetointegrador.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class AvaliacaoController {

	@Autowired
	AvaliacaoService avaliacaoService;

	@PostMapping(path = "/avaliacao")
	public ResponseEntity<?> registerAvaliacao(@RequestBody @Valid AvaliacaoDTO dto, UriComponentsBuilder uriBuilder) {
		URI uri = uriBuilder.path("/avaliacao").build().toUri();
		Avaliacao avaliacao = AvaliacaoDTO.convert(dto);
		return ResponseEntity.created(uri).body(avaliacaoService.createAvaliacao(avaliacao));
	}

	@PutMapping(path = "/avaliacao")
	public ResponseEntity<?> registerAvaliacao(@RequestBody @Valid AvaliacaoUpdateDTO dto,
											   UriComponentsBuilder uriBuilder) {
		URI uri = uriBuilder.path("/avaliacao").build().toUri();
		return ResponseEntity.created(uri).body(avaliacaoService.updateAvaliacao(dto));
	}

	@DeleteMapping(path = "/avaliacao/{id}")
	public ResponseEntity<?> deleteAvaliacao(@PathVariable Long id) {
		return ResponseEntity.ok(avaliacaoService.deleteAvaliacao(id));
	}

	@GetMapping(path = "/avaliacao/{id}")
	public ResponseEntity<?> getAvaliacao(@PathVariable Long id) {
		return ResponseEntity.ok(avaliacaoService.getAvaliacao(id));
	}

	@GetMapping(path = "/avaliacao/anuncio/{id}")
	public ResponseEntity<?> getAvaliacoesAnuncio(@PathVariable Long id) {
		return ResponseEntity.ok(avaliacaoService.getAvaliacoesAnuncio(id));
	}

}
