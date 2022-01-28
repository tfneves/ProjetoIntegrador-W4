package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.SetorDTO;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.service.SetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/setor")
public class SetorController {

	@Autowired
	SetorService setorService;

	@Autowired
	SetorDTO setorDTO;

	@GetMapping()
	public ResponseEntity<List<Setor>> devolveTodosOsSetores() {
		return ResponseEntity.ok(setorService.retornaTodosOsSetores());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastraSetor(@RequestBody @Valid SetorDTO payload, UriComponentsBuilder uriBuilder) throws Exception {
		Setor setor = setorDTO.converte(payload);
		URI uri = uriBuilder.path("/setor").build().toUri();
		try {
			return ResponseEntity.created(uri).body(setorService.salva(setor));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
