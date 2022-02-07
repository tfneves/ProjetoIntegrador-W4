package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.ArmazemDTO;
import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.service.ArmazemService;

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
public class ArmazemController {

	@Autowired
	private ArmazemService armazemService;

	/**
	 *
	 * Cadastra novo armazem no sistema
	 *
	 * @param dto
	 * @param uriBuilder
	 * @return ResponseEntity
	 */
	@PostMapping("/insertWareHouse")
	public ResponseEntity<Map<String, String>> insertWareHouse(@RequestBody @Valid ArmazemDTO dto,
			UriComponentsBuilder uriBuilder) {
		Map<String, String> response = new HashMap<>();
		try {
			Armazem armazem = ArmazemDTO.parseToEntity(dto);
			this.armazemService.save(armazem);
			URI uri = uriBuilder.path("/api/v1/getWareHouses").build().toUri();
			response.put("message", "Armazem criado com sucesso");
			return ResponseEntity.created(uri).body(response);
		} catch (Exception e) {
			response.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	/**
	 * Lista todos os armazens cadastrados
	 *
	 * @return ResponseEntity
	 */
	@GetMapping("/getWareHouses")
	public ResponseEntity<List<ArmazemDTO>> getAllWareHouses() {
		return ResponseEntity.ok().body(this.armazemService.findAll());
	}
}
