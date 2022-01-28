/**
package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.LoteDTO;
import br.com.meliw4.projetointegrador.entity.Lote;
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
	public ResponseEntity<List<Produto>> registerLote(@RequestBody @Valid LoteDTO loteDTO, UriComponentsBuilder uriBuilder) {
		URI uri = uriBuilder.path("").build().toUri();
		Lote lote = LoteDTO.convert(loteDTO);
		return ResponseEntity.created(uri).body(loteService.register(lote));
	}

	@PutMapping(path = "/fresh-products/inboundorder/")
	public ResponseEntity<List<Produto>> updateLote(@RequestBody @Valid LoteDTO loteDTO, UriComponentsBuilder uriBuilder) {
		URI uri = uriBuilder.path("").build().toUri();
		Lote lote = LoteDTO.convert(loteDTO);
		return ResponseEntity.created(uri).body(loteService.update(lote));
	}
}
**/
