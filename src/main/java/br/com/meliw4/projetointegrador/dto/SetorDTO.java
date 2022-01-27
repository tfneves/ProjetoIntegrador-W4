package br.com.meliw4.projetointegrador.dto;


import br.com.meliw4.projetointegrador.entity.Setor;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public Setor converte(SetorDTO payload){
        return Setor.builder()
                .categoria(payload.categoria)
                .volume(payload.volume)
                .build();
    }

    public SetorDTO converte(Setor payload){
        return SetorDTO.builder()
                .categoria(payload.getCategoria())
                .volume(payload.getVolume())
                .build();
    }

}
