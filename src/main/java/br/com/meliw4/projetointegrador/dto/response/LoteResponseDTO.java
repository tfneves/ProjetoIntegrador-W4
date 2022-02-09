package br.com.meliw4.projetointegrador.dto.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.meliw4.projetointegrador.entity.Lote;
import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoteResponseDTO {
	private Long setorId;
	private Long loteId;
	private Long produtoId;
	private Long anuncioId;
	private Categoria categoria;
	private LocalDate validade;
	private Integer quantidade;


}
