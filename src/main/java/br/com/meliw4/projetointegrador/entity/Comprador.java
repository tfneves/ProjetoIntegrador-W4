package br.com.meliw4.projetointegrador.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
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
	private LocalDate dataNascimento;


	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "endereco_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Endereco endereco;
}
