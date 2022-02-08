package br.com.meliw4.projetointegrador.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvaliacaoNPS {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_nps;
	@Column
	private Integer nota;
	@Column
	private LocalDate dataAvaliacao;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comprador_id")
	@JsonBackReference
	private Comprador comprador;
}
