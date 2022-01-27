package br.com.meliw4.projetointegrador.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Setor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSetor;
    @NotEmpty(message = "A Categoria não pode estar vazia")
    @NotNull()
    private String categoria;
    @NotEmpty(message = "O volume não pode estar vazia")
    @NotNull()
    private Double volume;

}
