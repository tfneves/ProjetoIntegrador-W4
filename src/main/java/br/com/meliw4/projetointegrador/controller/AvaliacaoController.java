package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.AvaliacaoDTO;
import br.com.meliw4.projetointegrador.dto.AvaliacaoUpdateDTO;
import br.com.meliw4.projetointegrador.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AvaliacaoController {

	@Autowired
	AvaliacaoService avaliacaoService;

	@PostMapping(path = "/avaliacao")
	public ResponseEntity<AvaliacaoDTO> createAvaliacao(@RequestBody @Valid AvaliacaoDTO dto,
														  UriComponentsBuilder uriBuilder) {
		URI uri = uriBuilder.path("/avaliacao").build().toUri();
		return ResponseEntity.created(uri).body(avaliacaoService.createAvaliacao(dto));
	}

	@PutMapping(path = "/avaliacao")
	public ResponseEntity<AvaliacaoDTO> updateAvaliacao(@RequestBody @Valid AvaliacaoUpdateDTO dto,
														  UriComponentsBuilder uriBuilder) {
		URI uri = uriBuilder.path("/avaliacao").build().toUri();
		return ResponseEntity.created(uri).body(avaliacaoService.updateAvaliacao(dto));
	}

	@DeleteMapping(path = "/avaliacao/{id}")
	public ResponseEntity<?> deleteAvaliacao(@PathVariable Long id) {
		avaliacaoService.deleteAvaliacao(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(path = "/avaliacao/{id}")
	public ResponseEntity<AvaliacaoDTO> getAvaliacao(@PathVariable Long id) {
		return ResponseEntity.ok(avaliacaoService.getAvaliacao(id));
	}

	@GetMapping(path = "/avaliacao/anuncio/{id}")
	public ResponseEntity<List<AvaliacaoDTO>> getAvaliacoesAnuncio(@PathVariable Long id) {
		return ResponseEntity.ok(avaliacaoService.getAvaliacoesAnuncio(id));
	}

}
