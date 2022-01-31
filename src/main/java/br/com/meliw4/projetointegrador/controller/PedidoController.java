package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.response.PedidoResponse;
import br.com.meliw4.projetointegrador.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PedidoController {

	@Autowired
	PedidoService pedidoService;

	@GetMapping("/fresh-products/orders/{id}")
	public ResponseEntity<PedidoResponse> getPedidoById(@PathVariable Long id) {
		return ResponseEntity.ok(pedidoService.getPedido(id));
	}
}
