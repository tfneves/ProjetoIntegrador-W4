package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.LoteDTO;
import br.com.meliw4.projetointegrador.dto.LoteUpdateDTO;
import br.com.meliw4.projetointegrador.dto.ProdutoDTO;
import br.com.meliw4.projetointegrador.service.LoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LoteController {

	@Autowired
	LoteService loteService;

	@PostMapping(path = "/fresh-products/inboundorder/")
	public ResponseEntity<List<ProdutoDTO>> registerLote(@RequestBody @Valid LoteDTO loteDTO,
														 UriComponentsBuilder uriBuilder) {
		URI uri = uriBuilder.path("").build().toUri();
		loteService.registerLote(loteDTO);
		return ResponseEntity.created(uri).body(loteDTO.getProdutosDTO());
	}

	@PutMapping(path = "/fresh-products/inboundorder/")
	public ResponseEntity<List<ProdutoDTO>> updateLote(@RequestBody @Valid LoteUpdateDTO loteUpdateDTO,
													   UriComponentsBuilder uriBuilder) {
		URI uri = uriBuilder.path("").build().toUri();
		return ResponseEntity.created(uri).body(loteService.updateLote(loteUpdateDTO));
	}

	@GetMapping(path = "/fresh-products/due-date/")
	public ResponseEntity<?> getLotesProdutosOrderedAndFilteredByDueDate(
		@RequestParam(value = "section", required = true) Long setorId,
		@RequestParam(value = "days", required = true) Integer days) {
		return ResponseEntity.ok(loteService.getLotesBySetorFilterProdutosByDays(setorId, days));
	}
}
