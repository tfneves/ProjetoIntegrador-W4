package br.com.meliw4.projetointegrador.config;

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
			.antMatchers("/anuncios").permitAll()
			.antMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()
			.antMatchers(HttpMethod.GET, "/vendas").hasAnyAuthority("ADMIN")
			.anyRequest().authenticated()
			.and().csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, repository), UsernamePasswordAuthenticationFilter.class);
	}
	//autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		auth.userDetailsService(autenticacaoService).passwordEncoder(encoder);
	}
	/*
	@Autowired
	private Environment env;

	public static final String[] PUBLIC_MATCHES = {
		"/h2-console/**",
		"/api/v1/**"
	};

	public static final String[] PUBLIC_MATCHES_GET = {
		"/api/v1/**"
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (Arrays.asList(env.getActiveProfiles()).contains("teste"))
			http.headers().frameOptions().disable();

		http.cors().and().csrf().disable();
		http.authorizeHttpRequests()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHES_GET).permitAll()
			.antMatchers(PUBLIC_MATCHES).permitAll()
			.anyRequest()
			.authenticated();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/",
			new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	*/

}
