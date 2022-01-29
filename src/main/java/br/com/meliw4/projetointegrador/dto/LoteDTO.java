
package br.com.meliw4.projetointegrador.dto;
import br.com.meliw4.projetointegrador.entity.Lote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoteDTO {

	@NotEmpty
	private Setor setor;
	@NotEmpty
	private List<@Valid ProdutoDTO> produtos;

	public static Lote convert(LoteDTO loteDTO) {
		return Lote.builder()
			.setor(loteDTO.setor)
			.produtos(loteDTO.produtos) // Mapear cada item ProdutoDTO para Produto
			.build();
	}

}


