package br.com.meliw4.projetointegrador.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @see Abstract Class EntidadeBase
 * @see Enum Tipo
 *
 * @author: André Arroxellas
 */

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produtos")
public class Produto extends EntidadeBase {

	@NotEmpty
	private LocalDate dataVencimento;

	@NotEmpty
	private Integer volume;

	@NotEmpty
	private int quantidadeInicial; // TODO: Integer for null

	@NotEmpty
	private int quantidadeAtual; // TODO: Integer for nul

	@NotEmpty
	private LocalDate dataManufatura;

	@NotEmpty
	private LocalDateTime dataStampManufatura;

	@ManyToOne
	@JoinColumn(name = "fk_vendedor", referencedColumnName = "id") // TODO: map as referencedColumnName = "CPF / CNPJ"
	private Vendedor vendedor;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_categoria", referencedColumnName = "tipo")
	private ProdutoCategoria produtoCategoria;
}
