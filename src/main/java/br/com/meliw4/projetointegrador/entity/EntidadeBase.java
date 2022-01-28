package br.com.meliw4.projetointegrador.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidade base abstrata
 * - Serializable
 * TODO: Mudade de Long id para UUID
 *
 * @author: André Arroxellas
 */

@Getter
@Setter
@MappedSuperclass
public class EntidadeBase implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Integer versao;
	@CreatedBy
	private String criadoPor;
	@LastModifiedBy
	private String atualizadoPor;
	@CreatedDate
	private LocalDateTime criadoEm;
	@LastModifiedDate
	private LocalDateTime atualizadoEm;

	// possibilita comparação lógica e por objeto
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		EntidadeBase that = (EntidadeBase) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(versao, that.versao);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, versao);
	}

	@Override
	public String toString() {
		return "BaseEntity {id=" + id + ", versao=" + versao + "}";
	}
}
