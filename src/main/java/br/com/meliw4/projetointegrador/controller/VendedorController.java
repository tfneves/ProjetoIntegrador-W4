package br.com.meliw4.projetointegrador.controller;
import br.com.meliw4.projetointegrador.dto.VendedorDTO;
import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import br.com.meliw4.projetointegrador.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class VendedorController {

	@Autowired
	private VendedorService vendedorService;

	@PostMapping(path = "/vendedor")
	public Vendedor registerVendedor(@RequestBody @Valid VendedorDTO vendedorDTO, UriComponentsBuilder uriBuilder) {
		Vendedor vendedor = VendedorDTO.convert(vendedorDTO);
		URI uri = uriBuilder.path("/api/v1/getVendedor").build().toUri();
		return vendedorService.register(vendedor);
	}
}
