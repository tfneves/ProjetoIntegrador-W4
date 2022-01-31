package br.com.meliw4.projetointegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProdutoCarrinho {

	@EmbeddedId
	private ProdutoCarrinhoId id;
	private Integer quantidade;
}

@Embeddable
class ProdutoCarrinhoId implements Serializable {
	@ManyToOne
	@JoinColumn(name = "carrinho_id")
	private Carrinho carrinho;

	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;
}
