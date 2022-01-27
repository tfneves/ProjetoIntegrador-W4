package br.com.meliw4.projetointegrador.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

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
@Table(name = "products")
public class Product extends BaseEntity {

	@NotEmpty
	private LocalDate dueDate;

	@NotEmpty
	private Integer volume;

	@NotEmpty
	private int initialQuantity; // TODO: Integer for null

	@NotEmpty
	private int currentQuantity; // TODO: Integer for nul

	@NotEmpty
	private LocalDate manufacturingDate = LocalDate.MIN;

	@NotEmpty
	private LocalDateTime manufacturingTime = LocalDateTime.MIN;

	@ManyToOne
	@JoinColumn(name = "fk_seller", referencedColumnName = "id") // TODO: map as referencedColumnName = "CPF / CNPJ"
	private Seller seller;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_category_id", referencedColumnName = "id")
	private ProductCategory productCategory;
}
