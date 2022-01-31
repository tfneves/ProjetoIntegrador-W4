package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.CompradorDTO;
import br.com.meliw4.projetointegrador.dto.EnderecoDTO;
import br.com.meliw4.projetointegrador.dto.RepresentanteDTO;
import br.com.meliw4.projetointegrador.entity.Comprador;
import br.com.meliw4.projetointegrador.entity.Endereco;
import br.com.meliw4.projetointegrador.repository.CompradorRepository;
import br.com.meliw4.projetointegrador.repository.EnderecoRepository;
import br.com.meliw4.projetointegrador.service.CompradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CompradorController {

	@Autowired
	CompradorService compradorService;

	Endereco endereco;
	/**
	 * Cadastra novo comprador no sistema
	 *
	 * @param compradorDTO
	 * @param uriBuilder
	 * @return ResponseEntity
	 * @Author Francisco Alves
	 */

	@PostMapping("/comprador")
	public ResponseEntity<Map<String, String>> cadastrarRepresentante(@RequestBody @Valid CompradorDTO compradorDTO, UriComponentsBuilder uriBuilder) throws Exception {
		Map<String, String> response = new HashMap<>();
		compradorService.register(compradorDTO);
		URI uri = uriBuilder.path("").build().toUri();
		response.put("message","Comprador criado com sucesso !!");
		return ResponseEntity.created(uri).body(response);
	}
}
