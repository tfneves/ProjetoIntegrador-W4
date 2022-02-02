package br.com.meliw4.projetointegrador.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProdutoCarrinho {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	@JoinColumn(name = "carrinho_id")
	private Carrinho carrinho;
	@ManyToOne
	@JoinColumn(name = "produto_vendedor_id")
	private ProdutoVendedor produto;
	private Integer quantidade;
}
