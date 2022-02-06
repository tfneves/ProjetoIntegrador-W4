package br.com.meliw4.projetointegrador.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

	private String user;
	private String senha;

	public UsernamePasswordAuthenticationToken converter() {

		return new UsernamePasswordAuthenticationToken(user, senha);
	}


}
