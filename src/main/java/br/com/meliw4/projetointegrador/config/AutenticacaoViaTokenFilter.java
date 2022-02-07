package br.com.meliw4.projetointegrador.config;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.repository.RepresentanteRepository;
import br.com.meliw4.projetointegrador.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


public class AutenticacaoViaTokenFilter extends OncePerRequestFilter{


	private TokenService tokenService;
	private RepresentanteRepository repository;

	public AutenticacaoViaTokenFilter(TokenService tokenService, RepresentanteRepository repository){
		this.tokenService = tokenService;
		this.repository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

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
		Representante usuario = this.repository.findByEmail(userName);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication); //forçando autenticacao pelo spring
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
