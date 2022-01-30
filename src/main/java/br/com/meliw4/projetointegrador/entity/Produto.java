package br.com.meliw4.projetointegrador.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @see Abstract Class EntidadeBase
 * @see Enum Tipo
 *
 * @author: André Arroxellas
 */

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produtos")
public class Produto extends EntidadeBase {

	@NotEmpty
	private LocalDate dataVencimento;

	@NotEmpty
	private Integer volume;

	@NotEmpty
	private int quantidadeInicial;

	@NotEmpty
	private int quantidadeAtual;

	@NotEmpty
	private LocalDate dataManufatura;

	@NotEmpty
	private LocalDateTime dataStampManufatura;

	@NotEmpty
	@Digits(integer = 3, fraction = 2, message = "Temperatura deve ser no formato XXX.XX")
	private Float temperaturaAtual;

	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "categoria_id", referencedColumnName = "categoria")
	private ProdutoCategoria produtoCategoria;
}
