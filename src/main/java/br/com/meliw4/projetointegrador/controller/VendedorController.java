package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.VendedorDTO;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import br.com.meliw4.projetointegrador.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class VendedorController {

	@Autowired
	private VendedorRepository vendedorRepository;

	/**
	 * Cadastra novo vendedor no sistema
	 *
	 * @param vendedorDTO
	 * @param uriBuilder
	 * @return ResponseEntity
	 */
	@PostMapping(path = "/vendedor")
	public ResponseEntity<Vendedor> registerRepresentante(@RequestBody @Valid VendedorDTO vendedorDTO,
			UriComponentsBuilder uriBuilder) {
		Vendedor vendedor = VendedorDTO.convert(vendedorDTO);
		vendedorRepository.save(vendedor);
		URI uri = uriBuilder.path("/api/v1/getVendedores").build().toUri();
		return ResponseEntity.created(uri).body(vendedor);
	}

	/**
	 * @Author Francisco Alves
	 *         Lista todos os vendedores cadastrados
	 * @return ResponseEntity
	 */
	@GetMapping("/getVendedores")
	public ResponseEntity<List<Vendedor>> getAllVendedores() {
		List<Vendedor> vendedor = vendedorRepository.findAll();
		return ResponseEntity.ok().body(vendedor);
	}
}
