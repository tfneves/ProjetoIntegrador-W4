
package br.com.meliw4.projetointegrador.entity;

import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Setor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "ENUM('FS','RR','FF')", length = 2)
	private Categoria categoria;
	private Double volume;
	@ManyToOne
	private Armazem armazem;
	@OneToMany(mappedBy = "setor", fetch = FetchType.LAZY)
	private List<Lote> lotes;

}
