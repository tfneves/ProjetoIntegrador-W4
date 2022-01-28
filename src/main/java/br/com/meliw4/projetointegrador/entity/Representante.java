package br.com.meliw4.projetointegrador.entity;

import br.com.meliw4.projetointegrador.dto.RepresentanteDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.Valid;


@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Representante{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

}
