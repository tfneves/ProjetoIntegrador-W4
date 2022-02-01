
package br.com.meliw4.projetointegrador.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import br.com.meliw4.projetointegrador.entity.enumeration.Tipo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @see Enum Tipo
 *
 * @author: André Arroxellas
 */

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categorias_produto")
public class ProdutoCategoria implements Serializable {

	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", columnDefinition = "ENUM('FRESH','REFRIGERATED','FROZEN')")
	private Tipo tipo;

	@NotEmpty
	@Digits(integer = 3, fraction = 2, message = "Temperatura deve ser no formato XXX.XX")
	private Float temperaturaAtual;

	@NotEmpty
	@Digits(integer = 3, fraction = 2, message = "Temperatura deve ser no formato XXX.XX")
	private Float temperaturaMinima;

	@OneToMany(mappedBy = "id")
	private List<Produto> produtos;
}

