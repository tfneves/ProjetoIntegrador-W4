package br.com.meliw4.projetointegrador.entity.enumeration;

import lombok.Getter;

/**
 * Abstract Base Entity
 * - Serializable
 *
 * @author: André Arroxellas
 */
@Getter
public enum Type {
	FRESH,
	REFRIGERATED,
	FROZEN;
}
