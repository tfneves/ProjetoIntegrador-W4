package br.com.meliw4.projetointegrador.entity;

import br.com.meliw4.projetointegrador.entity.enumeration.Estrelas;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassificacaoAvaliacao {

	@Id
	@Enumerated(EnumType.STRING)
	private Estrelas classificacao;
}
