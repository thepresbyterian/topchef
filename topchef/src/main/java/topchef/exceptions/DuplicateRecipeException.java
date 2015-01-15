package topchef.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="Duplicate recipe id.")
public class DuplicateRecipeException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public DuplicateRecipeException(String msg) {
		super(msg);
	}
	
	public DuplicateRecipeException() {
		super();
	}
}
