package br.com.meliw4.projetointegrador.config;

import br.com.meliw4.projetointegrador.advice.handler.CustomAccessDeniedHandler;
import br.com.meliw4.projetointegrador.exception.NotFoundException;
import br.com.meliw4.projetointegrador.repository.RepresentanteRepository;
import br.com.meliw4.projetointegrador.service.AutorizacaoService;
import br.com.meliw4.projetointegrador.service.TokenService;
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
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private AutorizacaoService autenticacaoService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private RepresentanteRepository repository;

	@Bean
	public CustomAccessDeniedHandler accessDeniedHandler(){
		return new CustomAccessDeniedHandler();
	}

	//autenticacao
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	//autorizacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()
			.anyRequest().authenticated()
			.and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(accessDeniedHandler())
			.and()
			.addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, repository), UsernamePasswordAuthenticationFilter.class);
	}

	//autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		auth.userDetailsService(autenticacaoService).passwordEncoder(encoder);
	}
}
