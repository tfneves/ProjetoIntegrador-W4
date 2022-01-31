package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.RepresentanteDTO;
import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.repository.RepresentanteRepository;
import br.com.meliw4.projetointegrador.service.RepresentanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RepresentanteController {

	@Autowired
	private RepresentanteRepository representanteRepository;

	/**
	 * Cadastra novo representante no sistema
	 * @Author Francisco Alves
	 * @param representanteDTO
	 * @param uriBuilder
	 * @return ResponseEntity
	 */
	@PostMapping(path = "/representante")
	public ResponseEntity<Representante> registerRepresentante(@RequestBody @Valid RepresentanteDTO representanteDTO, UriComponentsBuilder uriBuilder) {
		Representante representante = RepresentanteDTO.convert(representanteDTO);
		representanteRepository.save(representante);
		URI uri = uriBuilder.path("/api/v1/getRepresentante").build().toUri();
		return ResponseEntity.created(uri).body(representante);
	}
	/**
	 * @Author Francisco Alves
	 * Lista todos os representantes cadastrados
	 * @return ResponseEntity
	 */
	@GetMapping("/getRepresentante")
	public ResponseEntity<List<Representante>> getAllRepresentantes() {
		List<Representante> representantes = representanteRepository.findAll();
		return ResponseEntity.ok().body(representantes);
	}
}
