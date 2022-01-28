package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.Lote;
import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.repository.SetorRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Component
public class LoteDTO {

	@NotNull(message = "Id do setor inválido")
	private Long setorId;
	@NotNull(message = "Id do armazém inválido")
	private Long armazemId;
	@NotNull(message = "Id do vendedor inválido")
	private Long vendedorId;
	@NotNull(message = "Id do representante inválido")
	private Long representanteId;
	@NotEmpty
	private List<@Valid ProdutoDTO> produtosDTO;

	@Autowired
	private SetorRepository setorRepository;

	public Lote convert(LoteDTO loteDTO) {
		Setor setor = setorRepository.findById(loteDTO.setorId).get();
		return Lote.builder()
			.setor(setor)
			.build();
	}

}
