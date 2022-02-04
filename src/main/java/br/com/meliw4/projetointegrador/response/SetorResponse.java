package br.com.meliw4.projetointegrador.response;

import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.entity.Lote;
import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
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
