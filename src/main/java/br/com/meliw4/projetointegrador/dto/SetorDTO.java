package br.com.meliw4.projetointegrador.dto;


import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.repository.ArmazemRepository;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetorDTO {

    @NotEmpty(message = "A Categoria não pode estar vazia")
    private String categoria;
    @NotEmpty(message = "O volume não pode estar vazia")
    private Double volume;
    @NotEmpty
    private Long armazem_id;

    @Autowired
    ArmazemRepository armazemRepository;

    public Setor converte(SetorDTO payload){
        Armazem armazem = armazemRepository.getById(payload.armazem_id);
        return Setor.builder()
                .categoria(payload.categoria)
                .volume(payload.volume)
                .aemazem_id(armazem)
                .build();
    }

    public SetorDTO converte(Setor payload){
        return SetorDTO.builder()
                .categoria(payload.getCategoria())
                .volume(payload.getVolume())
                .armazem_id(payload.getAemazem_id().getArmazem_id())
                .build();
    }

}
