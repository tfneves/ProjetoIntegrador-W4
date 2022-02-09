package br.com.meliw4.projetointegrador.entity;

import br.com.meliw4.projetointegrador.entity.enumeration.Estrelas;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Avaliacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String comentario;

	@ManyToOne(optional = false)
	@Enumerated(EnumType.STRING)
	private ClassificacaoAvaliacao estrelas;

	@ManyToOne(optional = false)
	private ProdutoVendedor anuncio;

}
