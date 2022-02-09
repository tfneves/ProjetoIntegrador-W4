
package br.com.meliw4.projetointegrador.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @see Enum Tipo
 *
 * @author: André Arroxellas
 */

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produto_categorias")
public class ProdutoCategoria implements Serializable {

	@Id
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "ENUM('FS','RR','FF')", unique = true, length = 2)
	private Categoria categoria;

	@NotNull
	@Digits(integer = 3, fraction = 2, message = "Temperatura deve ser no formato XXX.XX")
	private Float temperaturaMinima;
}
