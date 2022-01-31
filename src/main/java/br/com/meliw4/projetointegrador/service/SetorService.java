package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.SetorDTO;
import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.repository.ArmazemRepository;
import br.com.meliw4.projetointegrador.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class SetorService {

	SetorRepository setorRepository;

	private SetorDTO setorDTO;

	public SetorService(SetorRepository setorRepository) {
		this.setorRepository = setorRepository;
	}

	@Autowired
	private ArmazemRepository armazemRepository;


	public Setor salva(Setor payload) {
		return setorRepository.save(payload);
	}

	public List<Setor> retornaTodosOsSetores() {
		return this.setorRepository.findAll();
	}

}

