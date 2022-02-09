package br.com.meliw4.projetointegrador.auth.config;

import br.com.meliw4.projetointegrador.auth.service.TokenService;
import br.com.meliw4.projetointegrador.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationService service;

	@Autowired
	TokenService tokenService;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	CustomAccessDeniedHandler customAccessDeniedHandler;

	@Autowired
	CustomAccessForbiddenHandler customAccessForbiddenHandler;

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()
			.antMatchers(HttpMethod.POST, "/api/v1/representante").permitAll()
			.antMatchers(HttpMethod.POST, "/api/v1/comprador").permitAll()
			.antMatchers(HttpMethod.POST, "/api/v1/vendedor").permitAll()
			.antMatchers(HttpMethod.POST, "/api/v1/insertWareHouse").permitAll()
			.antMatchers(HttpMethod.GET, "/api/v1/getWareHouses").hasAnyAuthority("Representante")
			.anyRequest().authenticated()
			.and().exceptionHandling().authenticationEntryPoint(customAccessDeniedHandler)
			.accessDeniedHandler(customAccessForbiddenHandler)
			.and().csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		auth.userDetailsService(service).passwordEncoder(encoder);
	}
}