package br.com.meliw4.projetointegrador.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Armazem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private Double volume;
	@OneToOne(mappedBy = "armazem")
	private Representante representante;
}
