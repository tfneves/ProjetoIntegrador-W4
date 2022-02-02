package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.RepresentanteDTO;
import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.exception.ArmazemException;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.RepresentanteRepository;
import org.springframework.stereotype.Service;

@Service
public class RepresentanteService {

	private RepresentanteRepository representanteRepository;
	private ArmazemService armazemService;

	public RepresentanteService(RepresentanteRepository representanteRepository, ArmazemService armazemService) {
		this.representanteRepository = representanteRepository;
		this.armazemService = armazemService;
	}

	public Representante register(RepresentanteDTO representanteDTO) {
		armazemService.validateArmazemExists(representanteDTO.getArmazem_id());
		Armazem armazem = armazemService.getArmazemById(representanteDTO.getArmazem_id());
		if (armazem.getRepresentante() != null) {
			throw new ArmazemException("O armazem tem representante cadastrado");
		}
		Representante representante = RepresentanteDTO.convert(representanteDTO, armazem);
		representanteRepository.save(representante);
		return representante;
	}

	public void validateRepresentanteExists(Long id) {
		if (!representanteRepository.existsById(id)) {
			throw new BusinessValidationException("O representante com id " + id + " não existe.");
		}
	}

	public void validateRepresentanteArmazem(Representante representante, Long armazemId) {
		if (representante.getArmazem().getId() != armazemId) {
			throw new BusinessValidationException("O representante não está associado a esse armazém.");
		}
	}

	public Representante getRepresentanteById(Long id) {
		return representanteRepository.getById(id);
	}
}
