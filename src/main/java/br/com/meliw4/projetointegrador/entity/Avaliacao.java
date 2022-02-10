package br.com.meliw4.projetointegrador.entity;

import br.com.meliw4.projetointegrador.entity.enumeration.Estrelas;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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

	private LocalDate dataAvaliacao;

	private String comentario;

	@ManyToOne
	private Comprador comprador;

	@ManyToOne
	@Enumerated(EnumType.STRING)
	private ClassificacaoAvaliacao estrelas;

	@ManyToOne
	private ProdutoVendedor anuncio;

	@ManyToOne
	private Carrinho pedido;

}
