package br.com.meliw4.projetointegrador.entity;

import java.util.List;

import javax.persistence.*;

import lombok.*;

/**
 * @see Enum Categoria
 **/

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String nome;

	private Double volume;

	@ManyToOne(optional = false)
	@JoinColumn(name = "categoria_id", referencedColumnName = "categoria")
	@Enumerated(EnumType.STRING)
	private ProdutoCategoria produtoCategoria;

	@Transient
	@OneToMany(mappedBy = "produto") // , fetch = FetchType.EAGER)
	private List<ProdutoVendedor> produtoVendedores;
}
