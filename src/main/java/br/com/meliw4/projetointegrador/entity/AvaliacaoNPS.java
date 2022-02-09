package br.com.meliw4.projetointegrador.entity;

import com.fasterxml.jackson.annotation.*;
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
	@Column(nullable = false)
	private Integer nota;
	@Column(name = "data_avaliacao" ,nullable = false ,columnDefinition = "DATE")
	private LocalDate dataAvaliacao;
	@OneToOne(cascade = {CascadeType.DETACH})
	@JoinColumn(name = "comprador_id")
	@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
	private Comprador comprador;
}
