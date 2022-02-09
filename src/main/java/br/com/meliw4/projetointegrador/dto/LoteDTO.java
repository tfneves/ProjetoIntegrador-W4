
package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.Lote;

import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.entity.Setor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoteDTO {

	@NotNull(message = "Id do setor inválido")
	private Long setorId;
	@NotNull(message = "Id do vendedor inválido")
	private Long vendedorId;
	@NotNull(message = "Id do representante inválido")
	private Long representanteId;
	@NotNull(message = "Id do armazém inválido")
	private Long armazemId;
	@NotEmpty
	private List<@Valid ProdutoDTO> produtosDTO;

	public static Lote convert(LoteDTO loteDTO, Setor setor, Representante representante) {
		return Lote.builder()
			.setor(setor)
			.representante(representante)
			.dataAquisicao(LocalDate.now())
			.build();
	}

}


