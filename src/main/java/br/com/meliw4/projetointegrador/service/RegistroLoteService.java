package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.entity.RegistroLote;
import br.com.meliw4.projetointegrador.repository.RegistroLoteRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistroLoteService {

	private RegistroLoteRepository registroLoteRepository;

	public RegistroLoteService(RegistroLoteRepository registroLoteRepository) {
		this.registroLoteRepository = registroLoteRepository;
	}

	public void save(RegistroLote registroLote) {
		registroLoteRepository.save(registroLote);
	}
}
