package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.CompradorDTO;
import br.com.meliw4.projetointegrador.dto.RepresentanteDTO;
import br.com.meliw4.projetointegrador.dto.VendedorDTO;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import br.com.meliw4.projetointegrador.repository.VendedorRepository;
import br.com.meliw4.projetointegrador.service.VendedorService;

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
public class VendedorController {

	@Autowired
	VendedorService vendedorService;

	/**
	 * Cadastra novo vendedor no sistema
	 *
	 * @param vendedorDTO
	 * @param uriBuilder
	 * @return ResponseEntity
	 */

	@PostMapping(path = "/vendedor")
	public ResponseEntity<Map<String, String>> cadastrarVendedor(@RequestBody @Valid VendedorDTO vendedorDTO,
			UriComponentsBuilder uriBuilder) throws Exception {
		Map<String, String> response = new HashMap<>();
		vendedorService.register(vendedorDTO);
		URI uri = uriBuilder.path("").build().toUri();
		response.put("message", "Vendedor criado com sucesso !!");
		return ResponseEntity.created(uri).body(response);
	}
}
