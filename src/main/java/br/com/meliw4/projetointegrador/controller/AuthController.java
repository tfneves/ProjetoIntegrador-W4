package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.TokenDTO;
import br.com.meliw4.projetointegrador.request.LoginRequest;
import br.com.meliw4.projetointegrador.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

	@Autowired
	private AuthenticationManager manager;
	@Autowired
	private TokenService tokenService;

	@PostMapping("/auth")
	public ResponseEntity<?> realizaAutenticacao(@RequestBody LoginRequest loginRequest) {
		try {
		UsernamePasswordAuthenticationToken dadosLogin = loginRequest.converter();
		Authentication authentication = manager.authenticate(dadosLogin);
		String token = tokenService.geraToken(authentication);
		return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
		}catch (BadCredentialsException e) {
			Map<String, String> response = new HashMap<>();
			response.put("msg: ", "Usuário ou Senha inválidos");
			return new ResponseEntity(response,HttpStatus.UNAUTHORIZED);

		}
	}
}
