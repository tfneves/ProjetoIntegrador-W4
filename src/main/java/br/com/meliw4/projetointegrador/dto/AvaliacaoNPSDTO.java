package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.AvaliacaoNPS;
import br.com.meliw4.projetointegrador.entity.Comprador;
import br.com.meliw4.projetointegrador.entity.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoNPSDTO {

	@NotNull(message = "O campo nota não pode ser vazio")
	private Integer nota;
	@NotNull(message = "O campo data avaliação não pode ser vazio")
	private LocalDate dataAvaliacao;
	@NotNull(message = "O campo comprador_id não pode ser vazio")
	private Long comprador_id;

	public static AvaliacaoNPS convert(AvaliacaoNPSDTO avaliacaoNPSDTO,Comprador comprador) {
		return AvaliacaoNPS.builder()
			.nota(avaliacaoNPSDTO.getNota())
			.dataAvaliacao(avaliacaoNPSDTO.getDataAvaliacao())
			.comprador(comprador)
			.build();
	}
}
