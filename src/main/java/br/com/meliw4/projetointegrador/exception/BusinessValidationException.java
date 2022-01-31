package br.com.meliw4.projetointegrador.exception;

public class BusinessValidationException extends RuntimeException{

	private static final long serialVersionUID = -1027971848760025355L;

	public BusinessValidationException(String message) {
		super(message);
	}
}
