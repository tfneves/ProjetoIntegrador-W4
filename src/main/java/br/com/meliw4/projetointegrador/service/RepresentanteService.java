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
		Armazem armazem = armazemService.findArmazemById(representanteDTO.getArmazem_id());
		if (armazem.getRepresentante() != null) {
			throw new ArmazemException("O armazem tem representante cadastrado");
		}

		Representante representante = RepresentanteDTO.convert(representanteDTO, armazem);
		return representanteRepository.save(representante);
	}

	public void validateRepresentanteArmazem(Representante representante, Long armazemId) {
		if (representante.getArmazem().getId() != armazemId) {
			throw new BusinessValidationException("O representante não está associado a esse armazém.");
		}
	}

	public Representante findRepresentanteById(Long id) {
		return representanteRepository
			.findById(id)
			.orElseThrow(() -> new BusinessValidationException("O representante com id " + id + " não existe."));
	}
}
