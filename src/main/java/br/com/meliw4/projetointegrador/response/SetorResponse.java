package br.com.meliw4.projetointegrador.response;

import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.entity.Lote;
import br.com.meliw4.projetointegrador.entity.enumeration.Tipo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetorResponse {
	private Long id;
	private Tipo categoria;
	private Double volume;
	private Long armazem_id;
	private List<Long> lote_id;
}
