package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.RepresentanteDTO;
import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.service.RepresentanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class RepresentanteController {

	@Autowired
	RepresentanteService representanteService;

	Representante representante;
	/**
	 * Cadastra novo representante no sistema
	 *
	 * @param representanteDTO
	 * @param uriBuilder
	 * @return ResponseEntity
	 * @Author Francisco Alves
	 */

	@PostMapping(path = "/representante")
	public ResponseEntity<Map<String, String>> cadastrarRepresentante(@RequestBody @Valid RepresentanteDTO representanteDTO, UriComponentsBuilder uriBuilder) throws Exception {
		Map<String, String> response = new HashMap<>();
		representanteService.register(representanteDTO);
		URI uri = uriBuilder.path("").build().toUri();
		response.put("message","Representante criado !!");
		return ResponseEntity.created(uri).body(response);
	}

	/**
	 * @return ResponseEntity
	 * @Author Francisco Alves
	 * Lista todos os representantes cadastrados
	 */

}
