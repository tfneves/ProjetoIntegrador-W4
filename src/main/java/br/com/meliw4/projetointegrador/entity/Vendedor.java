package br.com.meliw4.projetointegrador.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Vendedor extends Usuario implements UserDetails, GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;

	public Vendedor() {
		super();
	}

	public Vendedor(Long id, String login, String senha) {
		super(id, login, senha);
	}
	public Vendedor(String login, String senha, String nome) {
		super.setLogin(login);
		super.setSenha(senha);
		this.nome = nome;
	}

	@Override
	public String getAuthority() {
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("Vendedor"));
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
