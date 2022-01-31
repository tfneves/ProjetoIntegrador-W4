package br.com.meliw4.projetointegrador.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "armazem_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Armazem armazem;

}
