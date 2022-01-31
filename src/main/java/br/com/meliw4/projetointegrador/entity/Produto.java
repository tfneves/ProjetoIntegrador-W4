package br.com.meliw4.projetointegrador.entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.meliw4.projetointegrador.entity.enumeration.Tipo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: André Arroxellas
 * @see Abstract Class EntidadeBase
 * @see Enum Tipo
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
	@NotNull
	private LocalDate dataVencimento;
	@NotNull
	private Double volume;
	@NotNull
	private Integer quantidadeInicial;
	@NotNull
	private Integer quantidadeAtual;
	@NotNull
	private LocalDate dataManufatura;
	@NotNull
	private BigDecimal preco;
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private Lote lote;
	@NotNull
	private String nome;
	@JoinColumn(name = "fk_vendedor", referencedColumnName = "id")
	@ManyToOne
	private Vendedor vendedor;
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", columnDefinition = "ENUM('FRESCO','REFRIGERADO','CONGELADO')")
	private Tipo tipo;
	@NotNull
	@Digits(integer = 3, fraction = 2, message = "Temperatura deve ser no formato XXX.XX")
	private Float temperaturaAtual;
	@NotNull
	@Digits(integer = 3, fraction = 2, message = "Temperatura deve ser no formato XXX.XX")
	private Float temperaturaMinima;

	//@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	//@JoinColumn(name = "fk_categoria", referencedColumnName = "tipo")
	//private ProdutoCategoria produtoCategoria;
}
