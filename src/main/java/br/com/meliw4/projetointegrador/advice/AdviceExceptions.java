
package br.com.meliw4.projetointegrador.advice;

<<<<<<< HEAD
=======
import br.com.meliw4.projetointegrador.exception.ArmazemException;
>>>>>>> d1248bd9d01ab02e9e3a1a647cabcd298808e9f8
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AdviceExceptions {
<<<<<<< HEAD
=======

    /**
     * Trata exception de argumento inválido
     * @param e
     * @return Map
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private Map<String, String> argumentNotValidException(MethodArgumentNotValidException e){
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

>>>>>>> d1248bd9d01ab02e9e3a1a647cabcd298808e9f8
	/**
	 * Trata excessao relacionadas as classes de Armazem
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

<<<<<<< HEAD
	/**
	 * Trata exception de JSON Inválido
	 *
	 * @param e
	 * @return
	 */
=======

>>>>>>> d1248bd9d01ab02e9e3a1a647cabcd298808e9f8
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler(BusinessValidationException.class)
	private Map<String, String> businessValidationException(BusinessValidationException e) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error_message", e.getMessage());
		errors.put("statusCode", "400");
		return errors;
	}
<<<<<<< HEAD

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler(BusinessValidationException.class)
	private Map<String, String> businessValidationException(BusinessValidationException e) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error_message", e.getMessage());
		errors.put("statusCode", "400");
		return errors;
	}

=======
>>>>>>> d1248bd9d01ab02e9e3a1a647cabcd298808e9f8
}

