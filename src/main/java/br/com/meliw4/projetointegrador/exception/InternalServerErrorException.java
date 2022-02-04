package br.com.meliw4.projetointegrador.exception;

public class InternalServerErrorException extends RuntimeException{
	public InternalServerErrorException(String msg){
		super(msg);
	}
}
