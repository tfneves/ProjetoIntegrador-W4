package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.repository.AvaliacaoRepository;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {

	private AvaliacaoRepository avaliacaoRepository;

	public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
		this.avaliacaoRepository = avaliacaoRepository;
	}
}
