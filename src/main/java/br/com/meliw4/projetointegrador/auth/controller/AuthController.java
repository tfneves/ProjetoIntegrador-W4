package br.com.meliw4.projetointegrador.auth.controller;

import br.com.meliw4.projetointegrador.auth.dto.LoginDTO;
import br.com.meliw4.projetointegrador.auth.dto.TokenDTO;
import br.com.meliw4.projetointegrador.auth.service.TokenService;
import br.com.meliw4.projetointegrador.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private TokenService tokenService;

	@PostMapping("/auth")
	public ResponseEntity<?> authenticate(@RequestBody @Valid LoginDTO usuario) {
		UsernamePasswordAuthenticationToken dadosLogin = new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha());
		Authentication authentication = manager.authenticate(dadosLogin);
		String token = tokenService.geraToken(authentication);
		return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
	}
}
