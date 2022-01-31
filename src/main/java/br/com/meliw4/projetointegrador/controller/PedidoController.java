package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.CarrinhoDTO;
import br.com.meliw4.projetointegrador.dto.ProdutoPedidoDTO;
import br.com.meliw4.projetointegrador.entity.Carrinho;
import br.com.meliw4.projetointegrador.repository.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class PedidoController {

	@Autowired
	CarrinhoRepository carrinhoRepository;

	@PostMapping("/criaPedido")
	public ResponseEntity<?> criarPedido(@Valid @RequestBody CarrinhoDTO carrinhoDTO, UriComponentsBuilder uriComponentsBuilder) {
		URI uri = uriComponentsBuilder.path("").build().toUri();
		Carrinho carrinho = CarrinhoDTO.parseToEntity(carrinhoDTO);
		carrinhoRepository.save(carrinho);
		return ResponseEntity.created(uri).body(carrinho);
	}
}
