package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.AvaliacaoNPSDTO;
import br.com.meliw4.projetointegrador.dto.CompradorDTO;
import br.com.meliw4.projetointegrador.entity.AvaliacaoNPS;
import br.com.meliw4.projetointegrador.service.AvaliacaoNPSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/avaliacao")
public class AvaliacaoNPSController {

	@Autowired
	AvaliacaoNPSService as ;

	@PostMapping()
	public ResponseEntity<Map<String, String>> cadastrarAvaliacao(@RequestBody @Valid AvaliacaoNPSDTO avaliacaoNPSDTO, UriComponentsBuilder uriBuilder) throws Exception {
		Map<String, String> response = new HashMap<>();
		as.save(avaliacaoNPSDTO);
		URI uri = uriBuilder.path("").build().toUri();
		response.put("message","Sua avaliação foi registrada !!");
		return ResponseEntity.created(uri).body(response);
	}
	@GetMapping("/list")
	@ResponseBody
	private List<AvaliacaoNPS> getALL(){
		return as.getListAll() ;
	}
}
