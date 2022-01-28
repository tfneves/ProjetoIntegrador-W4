package br.com.meliw4.projetointegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class RegistroLote {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @Column(nullable = false)
    private Long representanteId;
	@Column(nullable = false)
    private Long vendedorId;
	@Column(nullable = false)
    private Long loteId;

}
