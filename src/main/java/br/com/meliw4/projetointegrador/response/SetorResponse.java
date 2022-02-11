package br.com.meliw4.projetointegrador.response;

import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetorResponse {
	private Long id;
	private Categoria categoria;
	private Double volume;
	private Long armazem_id;
	private List<Long> lote_id;
}
