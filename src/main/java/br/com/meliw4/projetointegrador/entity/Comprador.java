package br.com.meliw4.projetointegrador.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "comprador_teste")
public class Comprador extends Usuario implements UserDetails, GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String nome;
	@Column
	private String telefone;
	@Column
	private String email;
	@Column
	private LocalDate dataNascimento;


	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "endereco_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Endereco endereco;

	public Comprador() {
		super();
	}

	public Comprador(Long id, String login, String senha) {
		super(id, login, senha);
	}
	public Comprador(String login, String senha, String nome, String telefone, String email, LocalDate dataNascimento, Endereco endereco) {
		super.setLogin(login);
		super.setSenha(senha);
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
	}

	@Override
	public String getAuthority() {
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("Comprador"));
		return authorities;
	}

	@Override
	public String getPassword() {
		return super.getSenha();
	}

	@Override
	public String getUsername() {
		return super.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
