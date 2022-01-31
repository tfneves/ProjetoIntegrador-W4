package br.com.meliw4.projetointegrador.entity;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoVendedor {
	@EmbeddedId
	private ProdutoVendedorId produtoVendedorId;

	@Getter
	private BigDecimal preco;
}
