package br.com.meliw4.projetointegrador.entity.enumeration;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum Tipo {
	FRESCO("FR"),
	REFRIGERADO("RE"),
	CONGELADO("CO");

	private String ordinal;

	private Tipo(String ordinal) {
		this.ordinal = ordinal;
	}
}
