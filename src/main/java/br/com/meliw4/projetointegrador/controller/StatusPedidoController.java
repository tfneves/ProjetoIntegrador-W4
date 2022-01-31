package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.StatusPedidoDTO;
import br.com.meliw4.projetointegrador.entity.StatusPedido;
import br.com.meliw4.projetointegrador.repository.StatusPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class StatusPedidoController {

	@Autowired
	StatusPedidoRepository statusPedidoRepository;

	/**
	 *
	 * Realiza cadastro de um novo status de pedido
	 * @param dto
	 * @param uriBuilder
	 * @return ResponseEntity
	 */
	@PostMapping("/statusPedido/cadastrar")
	public ResponseEntity<Map<String, String>> cadastraStatusPedido(@Valid @RequestBody StatusPedidoDTO dto, UriComponentsBuilder uriBuilder) {
		Map<String, String> response = new HashMap<>();
		StatusPedido statusPedido = StatusPedidoDTO.parseToEntity(dto);
		try{
			statusPedidoRepository.save(statusPedido);
			URI uri = uriBuilder.path("/api/v1/statusPedido/listarTodos").build().toUri();
			response.put("message", "Status cadastrado com sucesso");
			return ResponseEntity.created(uri).body(response);
		}catch (Exception e){
			response.put("message", "Erro ao cadastrar status");
			response.put("message_exception", e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}


	/**
	 *
	 * Retorna todos os status de pedido salvos no BD
	 * @return ResponseEntity
	 */
	@GetMapping("/statusPedido/listarTodos")
	public ResponseEntity<List<StatusPedido>> listaStatusPedido() {
		List<StatusPedido> status = statusPedidoRepository.findAll();
		return ResponseEntity.ok().body(status);
	}
}
