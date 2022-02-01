package br.com.meliw4.projetointegrador.controller;

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
	public ResponseEntity<?> atulizarCarrinho(@RequestParam Long idOrder){
		return ResponseEntity.ok(carrinhoService.atualizaCarrinho(idOrder));
	}
}
