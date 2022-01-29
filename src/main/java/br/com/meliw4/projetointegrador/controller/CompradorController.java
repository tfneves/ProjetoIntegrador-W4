package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.CompradorDTO;
import br.com.meliw4.projetointegrador.dto.EnderecoDTO;
import br.com.meliw4.projetointegrador.entity.Comprador;
import br.com.meliw4.projetointegrador.entity.Endereco;
import br.com.meliw4.projetointegrador.repository.CompradorRepository;
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
public class CompradorController {

	@Autowired
	CompradorRepository compradorRepository;

	/**
	 * Cadastra novo comprador no sistema
	 *
	 * @param compradorDTO
	 * @param uriBuilder
	 * @return ResponseEntity
	 * @Author Francisco Alves
	 */
	@PostMapping(path = "/comprador")
	public ResponseEntity<Comprador> registerComprador(@RequestBody @Valid CompradorDTO compradorDTO, UriComponentsBuilder uriBuilder) {
		Comprador comprador = CompradorDTO.convert(compradorDTO);
		compradorRepository.save(comprador);
		URI uri = uriBuilder.path("/api/v1/getCompradores").build().toUri();
		return ResponseEntity.created(uri).body(comprador);
	}

	/**
	 * @return ResponseEntity
	 * @Author Francisco Alves
	 * Lista todos os bairros cadastrados
	 */
	@GetMapping("/getcompradores")
	public ResponseEntity<List<Comprador>> getAllCompradores() {
		List<Comprador> compradores = compradorRepository.findAll();
		return ResponseEntity.ok().body(compradores);
	}
}
