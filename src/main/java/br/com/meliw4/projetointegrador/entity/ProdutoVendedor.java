package br.com.meliw4.projetointegrador.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;

import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoVendedor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "vendedor_id")
	private Vendedor vendedor;

	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;

	private BigDecimal preco;

	@Digits(integer = 3, fraction = 2, message = "Temperatura deve ser no formato XXX.XX")
	private Float temperaturaAtual;

	private LocalDate dataVencimento;

	private LocalDate dataManufatura;

	private int quantidadeInicial;

	private int quantidadeAtual;

	@ManyToOne // (fetch = FetchType.EAGER)
	private Lote lote;

}
