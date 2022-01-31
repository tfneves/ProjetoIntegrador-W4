
package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.Lote;
<<<<<<< HEAD
import br.com.meliw4.projetointegrador.entity.Produto;
=======
>>>>>>> d1248bd9d01ab02e9e3a1a647cabcd298808e9f8
import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.entity.Setor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
	@NotEmpty
	private List<@Valid ProdutoDTO> produtosDTO;

	public static Lote convert(LoteDTO loteDTO, Setor setor, Representante representante) {
		return Lote.builder()
			.setor(setor)
			.representante(representante)
			.build();
	}

}
