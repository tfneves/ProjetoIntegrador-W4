package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.repository.RepresentanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepresentanteService {

    @Autowired
    private RepresentanteRepository representanteRepository;

    public Representante register(Representante representante) {
        return representanteRepository.save(representante);
    }
}
