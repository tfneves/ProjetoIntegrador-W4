package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.repository.ArmazemRepository;
import br.com.meliw4.projetointegrador.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetorService {

	private SetorRepository setorRepository;

	public SetorService(SetorRepository setorRepository) {
		this.setorRepository = setorRepository;
	}

	@Autowired
	private ArmazemRepository armazemRepository;


	public Setor salva(Setor payload) {
		if (possuiEspaco(payload))
			return setorRepository.save(payload);
		else
			throw new IllegalArgumentException("Espaço não disponível no armazem: " + payload.getArmazem().getNome());
	}

	public List<Setor> retornaTodosOsSetores() {
		return this.setorRepository.findAll();
	}

	private Boolean possuiEspaco(Setor setor) {
		Double volumeTotalSetores = volumeTotalDosSetores(setor);
		Double volumeTotalArmazem = setor.getArmazem().getVolume();
		return setor.getVolume() <= (volumeTotalArmazem - volumeTotalSetores);
	}

	private Double volumeTotalDosSetores(Setor setor){
		return setorRepository.findAll()
			.stream()
			.filter(s -> s.getArmazem() == setor.getArmazem())
			.map(s -> s.getVolume())
			.reduce( (n1, n2) -> n1 + n2 )
			.get();
	}
}
