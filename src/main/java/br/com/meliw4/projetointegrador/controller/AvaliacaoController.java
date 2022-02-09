package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AvaliacaoController {

	@Autowired
	AvaliacaoService avaliacaoService;
}
