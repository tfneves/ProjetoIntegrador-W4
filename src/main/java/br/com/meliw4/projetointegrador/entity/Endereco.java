
package br.com.meliw4.projetointegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String logradouro;
	@Column
	private Integer numero;
	@Column
	private String bairro;
	@Column
	private String cidade;
	@Column
	private String estado;
	@OneToOne(mappedBy = "endereco")
	private Comprador comprador;
}

