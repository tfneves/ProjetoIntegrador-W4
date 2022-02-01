package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.CarrinhoDTO;
import br.com.meliw4.projetointegrador.entity.Carrinho;
import br.com.meliw4.projetointegrador.repository.CarrinhoRepository;
import br.com.meliw4.projetointegrador.service.PedidoService;
import br.com.meliw4.projetointegrador.service.StatusPedidoService;
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
	PedidoService pedidoService;

	@PostMapping("/fresh-products/orders/createOrder")
	public ResponseEntity<?> criarPedido(@Valid @RequestBody CarrinhoDTO carrinhoDTO, UriComponentsBuilder uriComponentsBuilder) {
		if(pedidoService.salvaCarrinho(carrinhoDTO)) {
			// TODO: SALVAR PRODUTOS NA TABELA produto_carrinho
		}
		URI uri = uriComponentsBuilder.path("").build().toUri();
		return ResponseEntity.created(uri).body("Carrinho salvo com sucesso!");
	}


	/*@GetMapping("/fresh-products/orders/{id}")
	public ResponseEntity<PedidoResponse> getPedidoById(@PathVariable Long id) {
		return ResponseEntity.ok(pedidoService.getPedido(id));
	}*/
}
