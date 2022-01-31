package br.com.meliw4.projetointegrador.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4819027418282372193L;

	public NotFoundException(String message) {
		super(message);
	}
}
