package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.Representante;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Builder
public class RepresentanteDTO {

    @NotEmpty(message = "Nome não pode estar em branco")
    @Size(max = 100, message = "Nome não pode exceder 100 caracteres")
    private String nome;

    public static Representante convert(RepresentanteDTO representanteDTO){
        return Representante.builder().nome(representanteDTO.getNome()).build();
    }
}
