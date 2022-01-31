package br.com.meliw4.projetointegrador.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 //* @author: André Arroxellas
 //* @see Abstract Class EntidadeBase
 //* @see Enum Tipo
 **/

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String nome;

	private LocalDate dataVencimento;

	private Double volume;

	private int quantidadeInicial;

	private int quantidadeAtual;

	private LocalDate dataManufatura;

	// @NotEmpty
	// private LocalDateTime dataStampManufatura;

	@Digits(integer = 3, fraction = 2, message = "Temperatura deve ser no formato XXX.XX")
	private Float temperaturaAtual;

	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "categoria_id", referencedColumnName = "categoria")
	private ProdutoCategoria produtoCategoria;

	// @NotNull
	// @ManyToOne // (fetch = FetchType.EAGER)
	// private ProdutoVendedor produtoVendedor;

	@OneToMany(mappedBy = "produto", fetch = FetchType.EAGER)
	@Transient
	private List<ProdutoVendedor> produtoVendedores;

	@ManyToOne // (fetch = FetchType.EAGER)
	private Lote lote;

}
