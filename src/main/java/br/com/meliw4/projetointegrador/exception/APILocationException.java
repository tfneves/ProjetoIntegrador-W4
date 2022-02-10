package br.com.meliw4.projetointegrador.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class APILocationException extends RuntimeException {

	@Getter
	private String httpStatusCode = "4xx";

	public APILocationException(String msg, String httpStatusCode) {
		super(msg);
		this.httpStatusCode = httpStatusCode;
	}
}
