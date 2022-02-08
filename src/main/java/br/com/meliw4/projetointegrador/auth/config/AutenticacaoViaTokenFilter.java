package br.com.meliw4.projetointegrador.auth.config;

import br.com.meliw4.projetointegrador.auth.service.TokenService;
import br.com.meliw4.projetointegrador.entity.Comprador;
import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.entity.Usuario;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import br.com.meliw4.projetointegrador.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UsuarioRepository repository;

	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository repository){
		this.tokenService = tokenService;
		this.repository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		//obtem token do cabecalho da requisicao
		String token = extraiToken(request);
		//validar token
		boolean tokenValido = tokenService.tokenValido(token);

		if(tokenValido) {
			//autenticar o token
			realizaAutenticacaoDoTokenNoSpring(token);
		}
		filterChain.doFilter(request, response);
	}

	private void realizaAutenticacaoDoTokenNoSpring(String token) {
		String userName = tokenService.getUsername(token);
		Usuario usuario = this.repository.findByLogin(userName);
		if(usuario instanceof Representante){
			Representante representante = new Representante(usuario.getId(), usuario.getLogin(), usuario.getSenha());
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(representante, null, representante.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication); //forçando autenticacao pelo spring
		}else if(usuario instanceof Vendedor){
			Vendedor vendedor = new Vendedor(usuario.getId(), usuario.getLogin(), usuario.getSenha());
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(vendedor, null, vendedor.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication); //forçando autenticacao pelo spring
		}else{
			Comprador comprador = new Comprador(usuario.getId(), usuario.getLogin(), usuario.getSenha());
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(comprador, null, comprador.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication); //forçando autenticacao pelo spring
		}

	}

	private String extraiToken(HttpServletRequest request) {
		String token = "";
		String authorization = request.getHeader("Authorization");
		if(authorization==null || authorization.isEmpty() || !authorization.startsWith("Bearer ")) {
			return null;
		}else {
			token = authorization.substring(7, authorization.length());
		}
		return token;
	}
}
