package br.com.meliw4.projetointegrador.entity.enumeration;

import lombok.Getter;

@Getter
public enum Perfil {
	ADM("ROLE_ADM"),
	RPT("ROLE_REPRESENTANTE");

	private String role;

	private Perfil(String role) {
		this.role = role;
	}
}
