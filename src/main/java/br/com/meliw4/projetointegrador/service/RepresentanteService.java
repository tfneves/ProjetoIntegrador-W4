package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.RepresentanteDTO;
import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.exception.ArmazemException;
import br.com.meliw4.projetointegrador.repository.ArmazemRepository;
import br.com.meliw4.projetointegrador.repository.RepresentanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepresentanteService {

	@Autowired
	RepresentanteRepository representanteRepository;
	@Autowired
	ArmazemRepository armazemRepository;
	@Autowired
	ArmazemService armazemService;

	public Representante register(RepresentanteDTO representanteDTO) {

		if (!armazemService.existWareHouse(representanteDTO.getArmazem_id())) {
			throw new ArmazemException("Armazem não existe");
		}
		Armazem armazem = armazemRepository.getById(representanteDTO.getArmazem_id());
		if (armazem.getRepresentante()!=null) {
			throw new ArmazemException("O armazem tem representante cadastrado");
		}
		Representante representante = RepresentanteDTO.convert(representanteDTO, armazem);
		representanteRepository.save(representante);
		return representante;
	}
}
