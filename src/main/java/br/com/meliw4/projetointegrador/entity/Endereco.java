
package br.com.meliw4.projetointegrador.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
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

