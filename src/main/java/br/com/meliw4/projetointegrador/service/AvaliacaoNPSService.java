package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.AvaliacaoNPSDTO;
import br.com.meliw4.projetointegrador.entity.AvaliacaoNPS;
import br.com.meliw4.projetointegrador.repository.AvaliacaoNPSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class AvaliacaoNPSService {

	@Autowired
	AvaliacaoNPSRepository rep;

	public AvaliacaoNPS save(@Valid AvaliacaoNPSDTO avaliacaoNPSDTO) {
		AvaliacaoNPS avaliacaoNPS = AvaliacaoNPSDTO.convert(avaliacaoNPSDTO);
		rep.save(avaliacaoNPS);
		return avaliacaoNPS;
	}
}
