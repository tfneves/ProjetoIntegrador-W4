package br.com.meliw4.projetointegrador.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Abstract Base Entity
 * - Serializable
 * TODO: Change Long id to UUID
 *
 * @author: André Arroxellas
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public boolean isNew() {
		return this.id == null;
	}
}

// TODO: Complete implementation ?:
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// @MappedSuperclass
// public class BaseEntity implements Serializable {
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;
//
// @Version
// private Integer version;
//
// @Override
// public boolean equals(Object o) {
// if (this == o)
// return true;
// if (o == null || getClass() != o.getClass())
// return false;
// BaseEntity that = (BaseEntity) o;
// return Objects.equals(id, that.id) &&
// Objects.equals(version, that.version);
// }
//
// @Override
// public int hashCode() {
// return Objects.hash(id, version);
// }
//
// @Override
// public String toString() {
// return "BaseEntity {id=" + id + ", version=" + version + "}";
// }
