package br.com.meliw4.projetointegrador.dto;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendedorDTO {

    @NotEmpty(message = "Nome não pode estar em branco")
    @Size(max = 100, message = "Nome não pode exceder 100 caracteres")
    private String nome;
	@NotEmpty(message = "O campo login não pode ser vazio")
	@Size(max = 30, message = "O campo login não pode exceder 30 caracteres")
	private String login;
	@NotEmpty(message = "O campo senha não pode ser vazio")
	@Size(max = 30, message = "O campo senha não pode exceder 30 caracteres")
	private String senha;

	public static Vendedor convert(VendedorDTO vendedorDTO) {
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		return new Vendedor(vendedorDTO.login, bc.encode(vendedorDTO.senha), vendedorDTO.nome);
	}

}
