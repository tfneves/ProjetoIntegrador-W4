package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.service.OutboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/outbound")
public class OutboundOrderController {

	@Autowired
	private OutboundOrderService outboundOrderService;

	@GetMapping("/list")
	public ResponseEntity<Set<Long>> listaTodosOsCarrinhosParaOutBound(){
		return ResponseEntity.ok(outboundOrderService.listaTodosOsCarrinhosParaOutBound());
	}

	@GetMapping()
	public ResponseEntity<?> outboundCarrinhoPorId(@RequestParam Long idCarrinho){
		return ResponseEntity.ok(outboundOrderService.baixaOutBoundPorId(idCarrinho));
	}

	@GetMapping("/checkout")
	public ResponseEntity<?> confirmarOutboundPedido(@RequestParam Long idCarrinho){
		return ResponseEntity.ok(outboundOrderService.realizaOOutboundDoPedidoPorId(idCarrinho));
	}
}
