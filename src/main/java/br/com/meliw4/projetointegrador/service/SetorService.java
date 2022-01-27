package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.SetorDTO;
import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetorService {

    SetorRepository setorRepository;

    @Autowired
    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    SetorDTO setorDTO;

    public SetorDTO salva(SetorDTO payload) {
        Setor setor = this.setorRepository.save(setorDTO.converte(payload));
        return setorDTO.converte(setor);
    }

    public List<Setor> retornaTodosOsSetores(){
        return this.setorRepository.findAll();
    }
}
