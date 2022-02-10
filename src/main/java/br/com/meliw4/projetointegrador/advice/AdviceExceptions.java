
package br.com.meliw4.projetointegrador.advice;

import br.com.meliw4.projetointegrador.dto.ProdutoCarrinhoDTO;
import br.com.meliw4.projetointegrador.exception.*;

import br.com.meliw4.projetointegrador.repository.ProdutoVendedorRepository;
import br.com.meliw4.projetointegrador.service.PedidoService;
import br.com.meliw4.projetointegrador.service.ProdutoVendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class AdviceExceptions {

	@Autowired
	ProdutoVendedorService produtoVendedorRepository;

	/**
	 * Trata exception de argumento inválido
	 *
	 * @param e
	 * @return Map
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	private Map<String, String> argumentNotValidException(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();
		e.getBindingResult().getAllErrors().forEach((error) -> {
			String field = ((FieldError) error).getField();
			String errorMessage = ((FieldError) error).getDefaultMessage();
			errors.put("error_message", errorMessage);
		});
		return errors;
	}

	/**
	 * Trata exception de JSON Inválido
	 *
	 * @param e
	 * @return Map
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler(HttpMessageNotReadableException.class)
	private Map<String, String> jsonFormatterException(HttpMessageNotReadableException e) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error_message", "Json inválido");
		errors.put("exception_message", e.getMessage());
		return errors;
	}

	/**
	 * Trata excessao relacionadas as classes de Armazem
	 *
	 * @param e
	 * @return Map
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler(ArmazemException.class)
	private Map<String, String> armazemException(ArmazemException e) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error_message", e.getMessage());
		return errors;
	}

	/**
	 * Trata exception de JSON Inválido
	 *
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler(BusinessValidationException.class)
	private Map<String, String> businessValidationException(BusinessValidationException e) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error_message", e.getMessage());
		errors.put("statusCode", "400");
		return errors;
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	@ExceptionHandler(NotFoundException.class)
	private Map<String, String> notFoundException(NotFoundException e) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error_message", e.getMessage());
		errors.put("statusCode", "404");
		return errors;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	@ExceptionHandler(InternalServerErrorException.class)
	private Map<String, String> internalServerError(InternalServerErrorException e) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error_message", e.getMessage());
		errors.put("statusCode", "500");
		return errors;
	}

	@ResponseBody
	@ExceptionHandler(OrderCheckoutException.class)
	private ResponseEntity<Map<String, String>> orderCheckoutError(OrderCheckoutException e) {
		Map<String, String> response = new HashMap<>();
		response.put("error_message", e.getMessage());
		response.put("statusCode", Integer.toString(e.getHttpStatusCode()));
		produtoVendedorRepository.devolveProdutoEstoque(PedidoService.previousStateProdutoVendedor);
		PedidoService.clearQueueProdutoVendedor();
		if (e.getHttpStatusCode() == 400)
			return ResponseEntity.badRequest().body(response);
		return ResponseEntity.internalServerError().body(response);
	}

	@ResponseBody
	@ExceptionHandler(APILocationException.class)
	private Map<String, String> apiLocationException(APILocationException e) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error_message", e.getMessage());
		errors.put("statusCode", e.getHttpStatusCode());
		return errors;
	}
}
