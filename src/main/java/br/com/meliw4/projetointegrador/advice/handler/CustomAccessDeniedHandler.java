package br.com.meliw4.projetointegrador.advice.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException {
		res.setStatus(403);
		res.getWriter().write("Token invalido");
	}
}
