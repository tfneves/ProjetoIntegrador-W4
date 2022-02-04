package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.repository.ArmazemRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArmazemService {

    @Autowired
    ArmazemRepository armazemRepository;

    /**
     * Verifica se o armazem correspondente existe no banco de dados
     * @param id
     * @return boolean
     */
    public boolean existWareHouse(Long id) {
        return armazemRepository.existsById(id);
    }
}
