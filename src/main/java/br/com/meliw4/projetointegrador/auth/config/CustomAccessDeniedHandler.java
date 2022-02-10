package br.com.meliw4.projetointegrador.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		Map<String, String> resp = new HashMap<>();
		response.setContentType("application/json;charset=UTF-8");
		if(authException.getMessage().equals("Bad credentials")){
			response.setStatus(401);
			resp.put("message", "Credenciais inválidas");
		}else if(authException.getMessage().equals("Full authentication is required to access this resource")){
			response.setStatus(400);
			resp.put("message", "Token inválido ou incompleto");
		}
		else{
			response.setStatus(404);
			resp.put("message", authException.getMessage());
		}
		response.getWriter().write(new ObjectMapper().writeValueAsString(resp));
	}
}
