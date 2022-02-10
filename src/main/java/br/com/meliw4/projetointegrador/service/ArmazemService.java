package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.ArmazemDTO;
import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.ArmazemRepository;

import java.util.List;
import java.util.stream.Collectors;

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

	public List<Armazem> findAll() {
		return this.armazemRepository.findAll();
	}

	public List<ArmazemDTO> findAllDTO() {
		return this.armazemRepository.findAll().stream()
				.map(ArmazemDTO::parseToDTO)
				.collect(Collectors.toList());
	}

	public void save(Armazem armazem) {
		armazemRepository.save(armazem);
	}
}
