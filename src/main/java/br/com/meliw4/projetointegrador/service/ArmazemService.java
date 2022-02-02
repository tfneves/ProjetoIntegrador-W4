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


	/**
	 * Verifica se o armazem correspondente existe no banco de dados
	 *
	 * @param id
	 * @return boolean
	 */
	public void validateArmazemExists(Long id) {
		if (!armazemRepository.existsById(id)) {
			throw new BusinessValidationException("O armazém com id " + id + " não existe.");
		}
	}

	public Armazem getArmazemById(Long id) {
		return armazemRepository.getById(id);
	}
}
