package br.com.meliw4.projetointegrador.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	private ProdutoCategoria produtoCategoria;

	@Transient
	@OneToMany(mappedBy = "produto") // , fetch = FetchType.EAGER)
	private List<ProdutoVendedor> produtoVendedores;
}
