package br.com.meliw4.projetointegrador.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;


@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Representante{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "armazem_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Armazem armazem;

}
