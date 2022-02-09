package br.com.meliw4.projetointegrador.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessForbiddenHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		Map<String, String> resp = new HashMap<>();
		response.setContentType("application/json;charset=UTF-8");
		if(accessDeniedException.getMessage().equals("Access is denied")){
			response.setStatus(403);
			resp.put("message", "Usuário não autorizado");
		}else{
			response.setStatus(500);
			resp.put("message", accessDeniedException.getMessage());
		}
		response.getWriter().write(new ObjectMapper().writeValueAsString(resp));
	}
}
