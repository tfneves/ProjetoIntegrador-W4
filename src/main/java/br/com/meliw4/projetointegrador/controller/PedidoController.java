package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.CarrinhoDTO;
import br.com.meliw4.projetointegrador.dto.UpdateCartStatusDTO;
import br.com.meliw4.projetointegrador.entity.Carrinho;
import br.com.meliw4.projetointegrador.repository.CarrinhoRepository;
import br.com.meliw4.projetointegrador.response.PedidoResponse;
import br.com.meliw4.projetointegrador.service.PedidoService;
import br.com.meliw4.projetointegrador.service.StatusPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class PedidoController {

	@Autowired
	PedidoService pedidoService;

	/**
	 * Cria pedido
	 * @author Thomaz Ferreira
	 * @param carrinhoDTO
	 * @param uriComponentsBuilder
	 * @return ResponseEntity
	 */
	@PostMapping("/fresh-products/orders/createOrder")
	public ResponseEntity<?> criarPedido(@Valid @RequestBody CarrinhoDTO carrinhoDTO, UriComponentsBuilder uriComponentsBuilder) {
		Long carrinhoId = pedidoService.salvaPedido(carrinhoDTO);
		if(carrinhoId != null){
			BigDecimal valorTotalCarrinho = pedidoService.calculaValorTotalCarrinho(carrinhoId);
			Map<String, BigDecimal> response = new HashMap<>();
			response.put("PrecoTotal", valorTotalCarrinho);
			URI uri = uriComponentsBuilder.path("").build().toUri();
			return ResponseEntity.created(uri).body(response);
		}
		Map<String, String> response = new HashMap<>();
		response.put("message", "Falha ao salvar carrinho");
		return ResponseEntity.internalServerError().body(response);
	}


	@GetMapping("/fresh-products/orders/{id}")
	public ResponseEntity<PedidoResponse> getPedidoById(@PathVariable Long id) {
		return ResponseEntity.ok(pedidoService.getPedido(id));
	}


	@PutMapping("/orders/updateCartStatus")
	public ResponseEntity<Map<String, String>> updateCartStatus(@RequestBody @Valid UpdateCartStatusDTO updateCartStatusDTO) {
		final Long TIMEOUT = 15000L;
		Map<String, String> response = new HashMap<>();
		if(pedidoService.excluiCarrinho(updateCartStatusDTO, TIMEOUT)){
			response.put("message", "Status atualizado com sucesso");
			return ResponseEntity.ok().body(response);
		}
		response.put("message", "Falha ao atualizar o status do carrinho");
		return ResponseEntity.internalServerError().body(response);
	}
}
