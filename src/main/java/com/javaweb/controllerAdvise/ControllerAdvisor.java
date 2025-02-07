package com.javaweb.controllerAdvise;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.javaweb.Model.ErrorResponeDTO;
import com.javaweb.customExcept.CustomRequiredException;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	@ExceptionHandler(ArithmeticException.class)
	public ResponseEntity<Object> handleArithmeticException(ArithmeticException ex, WebRequest request) {
		ErrorResponeDTO err = new ErrorResponeDTO();
		err.setError(ex.getMessage());
		List<String> detail = new ArrayList<>();
		detail.add("/ by zero");
		err.setDetails(detail);
		return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CustomRequiredException.class)
	public ResponseEntity<Object> handleCustomRequiredException(CustomRequiredException ex, WebRequest request) {
		ErrorResponeDTO err = new ErrorResponeDTO();
		err.setError(ex.getMessage());
		List<String> detail = new ArrayList<>();
		detail.add("Name or num not null");
		err.setDetails(detail);
		return new ResponseEntity<>(err, HttpStatus.BAD_GATEWAY);
	}
}
