package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.Vendedor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class VendedorDTO {

    @NotEmpty(message = "Nome não pode estar em branco")
    @Size(max = 100, message = "Nome não pode exceder 100 caracteres")
    private String nome;

    public static Vendedor convert(VendedorDTO vendedorDTO){
        return Vendedor.builder().nome(vendedorDTO.nome).build();
    }

}
