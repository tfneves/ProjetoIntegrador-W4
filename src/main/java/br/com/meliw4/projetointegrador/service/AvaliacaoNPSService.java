package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.ArmazemDTO;
import br.com.meliw4.projetointegrador.dto.AvaliacaoNPSDTO;
import br.com.meliw4.projetointegrador.entity.AvaliacaoNPS;
import br.com.meliw4.projetointegrador.entity.Comprador;
import br.com.meliw4.projetointegrador.repository.AvaliacaoNPSRepository;
import br.com.meliw4.projetointegrador.repository.CompradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliacaoNPSService {
	@Autowired
	CompradorService compradorService;
	@Autowired
	CompradorRepository compradorRepository;
	@Autowired
	AvaliacaoNPSRepository rep;

	/**
	 * Salva avaliaçãoNPS nas tabelas pertinentes
	 * @return avaliacaoNPS
	 * @author Francisco Alves
	 */
	public AvaliacaoNPS save(@Valid AvaliacaoNPSDTO avaliacaoNPSDTO ) {
		Comprador comprador = compradorRepository.getById(avaliacaoNPSDTO.getComprador_id());
		AvaliacaoNPS avaliacaoNPS = AvaliacaoNPSDTO.convert(avaliacaoNPSDTO,comprador);
		rep.save(avaliacaoNPS);
		return avaliacaoNPS;
	}
	/**
	 * Lista todas as  avaliaçãoNPS nas tabelas pertinentes
	 * @return rep.finall()
	 * @author Francisco Alves
	 */
	public List<AvaliacaoNPS> getListAll(){
		return rep.findAll();
	}
}
