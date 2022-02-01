package br.com.meliw4.projetointegrador.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class RegistroLote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Representante representante;

	@ManyToOne
	private Vendedor vendedor;

	@ManyToOne
	private Lote lote;

}

