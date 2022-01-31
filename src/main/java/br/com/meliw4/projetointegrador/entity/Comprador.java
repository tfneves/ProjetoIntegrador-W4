package br.com.meliw4.projetointegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Comprador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String nome;
	@Column
	private String telefone;
	@Column
	private String email;
	@Column
	private String dataNascimento;

	@ManyToOne
	@JoinColumn(name = "endereco_id")
	private Endereco endereco;
}
