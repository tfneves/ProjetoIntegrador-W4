/*
package br.com.meliw4.projetointegrador.controller;
*/
/*
import br.com.meliw4.projetointegrador.dto.SetorDTO;

import java.util.List;

import br.com.meliw4.projetointegrador.entity.Setor;
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

	@Autowired
	SetorDTO setorDTO;

	@GetMapping("/setor")
	public ResponseEntity<List<Setor>> devolveTodosOsSetores() {
		return ResponseEntity.ok(setorService.retornaTodosOsSetores());
	}

	@PostMapping("/setor")
	public ResponseEntity<Setor> cadastraSetor(@RequestBody @Valid SetorDTO payload, UriComponentsBuilder uriBuilder) throws Exception {
		Setor setor = setorDTO.converte(payload);
		return ResponseEntity.ok(setorService.salva(setor));
	}

}
*/
