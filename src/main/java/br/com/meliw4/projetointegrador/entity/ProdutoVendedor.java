package br.com.meliw4.projetointegrador.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoVendedor {
	@EmbeddedId
	private ProdutoVendedorId id;

	@NotEmpty
	private BigDecimal preco;
}

@Embeddable
class ProdutoVendedorId implements Serializable {
	@ManyToOne
	@JoinColumn(name = "vendedor_id")
	private Vendedor vendedor;

	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;
}
