
package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.SetorDTO;

import java.net.URI;
import java.util.List;

import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.exception.ArmazemException;
import br.com.meliw4.projetointegrador.response.SetorResponse;
import br.com.meliw4.projetointegrador.service.SetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class SetorController {

	@Autowired
	SetorService setorService;

	@GetMapping("/setor")
	public ResponseEntity<List<SetorResponse>> devolveTodosOsSetores() {
		return ResponseEntity.ok(setorService.retornaSetores());
	}

	@PostMapping("/setor")
	public ResponseEntity<?> cadastraSetor(@RequestBody @Valid SetorDTO payload,
			UriComponentsBuilder uriBuilder) throws ArmazemException {
		URI uri = uriBuilder.path("/setor").build().toUri();
		try {
			return ResponseEntity.created(uri).body(setorService.salva(payload));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
