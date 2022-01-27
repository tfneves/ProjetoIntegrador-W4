package br.com.meliw4.projetointegrador.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import br.com.meliw4.projetointegrador.entity.enumeration.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @see Abstract Class BaseEntity
 * @see Enum Type
 *
 * @author: André Arroxellas
 */

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_category")
public class ProductCategory implements Serializable {

	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "id", columnDefinition = "ENUM('FRESH','REFRIGERATED','FROZEN')")
	private Type type;

	@NotEmpty
	@Digits(integer = 3, fraction = 2, message = "")
	private Float currentTemperature;

	@NotEmpty
	@Digits(integer = 3, fraction = 2, message = "")
	private Float mininumTemperature;

	@OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL)
	private List<Product> products;
}
