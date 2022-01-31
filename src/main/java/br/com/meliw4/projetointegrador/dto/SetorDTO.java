package br.com.meliw4.projetointegrador.dto;


import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.entity.Lote;
import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.entity.enumeration.Tipo;
import br.com.meliw4.projetointegrador.repository.ArmazemRepository;
import br.com.meliw4.projetointegrador.exception.ArmazemException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SetorDTO {

	@NotNull(message = "Categoria inválida")
	private Tipo categoria;
	@NotNull(message = "Volume inválido")
	private Double volume;
	@NotNull(message = "Armazém inválido")
	private Long armazem_id;
	@NotEmpty
	private Double espaco_disponivel;
	@NotEmpty
	private List<Lote> lotes;

	@Autowired
	ArmazemRepository armazemRepository;

	public Setor converte(SetorDTO payload) {
		Armazem armazem = armazemRepository.findById(payload.armazem_id).orElseThrow(
			() -> new ArmazemException("O armazém informado não está cadastrado no sistema"));
		return Setor.builder()
			.categoria(payload.categoria)
			.volume(payload.volume)
			.armazem(armazem)
			.build();
	}

	public SetorDTO converte(Setor payload){
		return SetorDTO.builder()
			.categoria(payload.getCategoria())
			.volume(payload.getVolume())
			.armazem_id(payload.getArmazem().getId())
			.build();
	}


}
