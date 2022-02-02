package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.ArmazemRepository;
import org.springframework.stereotype.Service;

@Service
public class ArmazemService {


	private ArmazemRepository armazemRepository;

	public ArmazemService(ArmazemRepository armazemRepository) {
		this.armazemRepository = armazemRepository;
	}

	public Armazem findArmazemById(Long id) {
		return armazemRepository
			.findById(id)
			.orElseThrow(() -> new BusinessValidationException("O armazém com id " + id + " não existe."));
	}
}
