package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.entity.Carrinho;
import br.com.meliw4.projetointegrador.entity.StatusPedido;
import br.com.meliw4.projetointegrador.response.CarrinhoResponse;
import br.com.meliw4.projetointegrador.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CarrinhoController {

	@Autowired
	CarrinhoService carrinhoService;

	@PutMapping("fresh-products/orders/")
	public ResponseEntity<CarrinhoResponse> atulizarCarrinho(@RequestParam Long idOrder, @RequestBody StatusPedido statusPedido){
		return ResponseEntity.ok(carrinhoService.atualizaCarrinho(idOrder, statusPedido));
	}
}
