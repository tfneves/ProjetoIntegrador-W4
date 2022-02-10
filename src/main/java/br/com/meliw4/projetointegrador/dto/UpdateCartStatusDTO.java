package br.com.meliw4.projetointegrador.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartStatusDTO {
	private Long carrinho_id;
	private String statusCode;
}
