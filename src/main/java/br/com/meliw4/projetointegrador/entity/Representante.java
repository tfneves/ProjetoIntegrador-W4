package br.com.meliw4.projetointegrador.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
public class Representante extends Usuario implements UserDetails, GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "armazem_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Armazem armazem;

	public Representante() {
		super();
	}

	public Representante(Long id, String login, String senha) {
		super(id, login, senha);
	}
	public Representante(String login, String senha, String nome, Armazem armazem) {
		super.setLogin(login);
		super.setSenha(senha);
		this.nome = nome;
		this.armazem = armazem;
	}

	@Override
	public String getAuthority() {
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("Representante"));
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
