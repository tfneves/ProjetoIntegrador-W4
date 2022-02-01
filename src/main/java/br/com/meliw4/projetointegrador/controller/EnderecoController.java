package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.EnderecoDTO;
import br.com.meliw4.projetointegrador.dto.RepresentanteDTO;
import br.com.meliw4.projetointegrador.entity.Endereco;
import br.com.meliw4.projetointegrador.repository.EnderecoRepository;
import br.com.meliw4.projetointegrador.service.EnderecoService;
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
public class EnderecoController {

	@Autowired
	EnderecoService enderecoService;

	/**
	 * Cadastra novo endereco no sistema
	 *
	 * @param enderecoDTO
	 * @param uriBuilder
	 * @return ResponseEntity
	 * @Author Francisco Alves
	 */
	@PostMapping(path = "/endereco")
	public ResponseEntity<Map<String, String>> cadastrarEndereco(@RequestBody @Valid EnderecoDTO enderecoDTO, UriComponentsBuilder uriBuilder) throws Exception {
		Map<String, String> response = new HashMap<>();
		enderecoService.register(enderecoDTO);
		URI uri = uriBuilder.path("").build().toUri();
		response.put("message","Endereco criado com sucesso!!");
		return ResponseEntity.created(uri).body(response);
	}
}
