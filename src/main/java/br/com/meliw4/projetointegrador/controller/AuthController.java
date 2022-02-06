package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.TokenDTO;
import br.com.meliw4.projetointegrador.request.LoginRequest;
import br.com.meliw4.projetointegrador.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

	@Autowired
	private AuthenticationManager manager;
	@Autowired
	private TokenService tokenService;

	@PostMapping("/auth")
	public ResponseEntity<?> realizaAutenticacao(@RequestBody LoginRequest loginRequest) {
		UsernamePasswordAuthenticationToken dadosLogin = loginRequest.converter();
		Authentication authentication = manager.authenticate(dadosLogin);
		String token = tokenService.geraToken(authentication);
		return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
	}
}
