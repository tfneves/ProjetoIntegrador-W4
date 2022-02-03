package br.com.meliw4.projetointegrador.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class OrderCheckoutException extends RuntimeException{

	@Getter
	private Integer httpStatusCode;

	public OrderCheckoutException(String msg, Integer httpStatusCode) {
		super(msg);
		this.httpStatusCode = httpStatusCode;
	}
}
