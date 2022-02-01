package br.com.meliw4.projetointegrador.entity.enumeration;

import lombok.Getter;

@Getter
public enum Categoria {
	FS("FRESCO"),
	RR("REFRIGERADO"),
	FF("CONGELADO");

	private String ordinal;

	private Categoria(String ordinal) {
		this.ordinal = ordinal;
	}
}
