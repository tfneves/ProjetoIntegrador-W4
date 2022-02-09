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
	private UsuarioService usuarioService;

	public RepresentanteService(RepresentanteRepository representanteRepository, ArmazemService armazemService, UsuarioService usuarioService) {
		this.representanteRepository = representanteRepository;
		this.armazemService = armazemService;
		this.usuarioService = usuarioService;
	}

	public Representante register(RepresentanteDTO representanteDTO) {
		Armazem armazem = armazemService.findArmazemById(representanteDTO.getArmazem_id());
		if (armazem.getRepresentante() != null) {
			throw new ArmazemException("O armazém já tem um representante cadastrado.");
		}

		Representante representante = RepresentanteDTO.convert(representanteDTO, armazem);
		if(!usuarioService.usuarioCadastrado(representanteDTO.getLogin()))
			throw new BusinessValidationException("Login já existente na base de dados");
		representanteRepository.save(representante);
		return representante;
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
