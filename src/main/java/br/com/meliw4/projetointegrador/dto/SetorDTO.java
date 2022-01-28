package br.com.meliw4.projetointegrador.dto;


import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.repository.ArmazemRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SetorDTO {

	@NotEmpty(message = "A Categoria não pode estar vazia")
	private String categoria;
	@NotEmpty(message = "O volume não pode estar vazia")
	private Double volume;
	@NotEmpty
	private Long armazem_id;

	@Autowired
	ArmazemRepository armazemRepository;

	public Setor converte(SetorDTO payload){
		Armazem armazem = armazemRepository.findById(payload.armazem_id).get();
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
