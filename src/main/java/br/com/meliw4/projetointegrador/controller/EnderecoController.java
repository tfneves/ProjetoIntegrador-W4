package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.EnderecoDTO;
import br.com.meliw4.projetointegrador.entity.Endereco;
import br.com.meliw4.projetointegrador.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EnderecoController {

	@Autowired
	EnderecoRepository enderecoRepository;

	/**
	 * Cadastra novo endereco no sistema
	 *
	 * @param enderecoDTO
	 * @param uriBuilder
	 * @return ResponseEntity
	 * @Author Francisco Alves
	 */
	@PostMapping(path = "/endereco")
	public ResponseEntity<Endereco> registerEndereco(@RequestBody @Valid EnderecoDTO enderecoDTO, UriComponentsBuilder uriBuilder) {
		Endereco endereco = EnderecoDTO.convert(enderecoDTO);
		enderecoRepository.save(endereco);
		URI uri = uriBuilder.path("/api/v1/getEnderecos").build().toUri();
		return ResponseEntity.created(uri).body(endereco);
	}

	/**
	 * @return ResponseEntity
	 * @Author Francisco Alves
	 * Lista todos os bairros cadastrados
	 */
	@GetMapping("/getEnderecos")
	public ResponseEntity<List<Endereco>> getAllEndereco() {
		List<Endereco> endereco = enderecoRepository.findAll();
		return ResponseEntity.ok().body(endereco);
	}


}
