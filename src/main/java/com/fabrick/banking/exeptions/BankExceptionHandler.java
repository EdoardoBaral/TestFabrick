package com.fabrick.banking.exeptions;

import com.fabrick.banking.interfaces.CreditTransferResponse;
import com.fabrick.banking.interfaces.GenericResponse;
import com.fabrick.banking.model.ErrorModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class BankExceptionHandler
{
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<GenericResponse> handleException(ConstraintViolationException ex)
	{
		GenericResponse response = new GenericResponse();
		response.setStatus(GenericResponse.STATUS_KO);
		response.setStatusCode(HttpStatus.BAD_REQUEST);
		
		List<ErrorModel> errorList = new ArrayList<>();
		ex.getConstraintViolations().forEach(violation -> {
			ErrorModel errorModel = new ErrorModel();
			errorModel.setDescription(violation.getMessageTemplate());
			errorList.add(errorModel);
		});
		
		response.setError(errorList);
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<GenericResponse> handleException(MethodArgumentNotValidException ex)
	{
		GenericResponse response = new GenericResponse();
		response.setStatus(GenericResponse.STATUS_KO);
		response.setStatusCode(HttpStatus.BAD_REQUEST);
		
		List<ErrorModel> errorList = new ArrayList<>();
		ErrorModel errorModel = new ErrorModel();
		errorModel.setDescription(ex.getFieldError().getDefaultMessage());
		errorList.add(errorModel);
		
		response.setError(errorList);
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<CreditTransferResponse> handleException(HttpClientErrorException ex) throws JsonProcessingException
	{
		String detailMessage = ex.getMessage();
		detailMessage = detailMessage.substring(detailMessage.indexOf("{"));
		detailMessage = detailMessage.replace("<EOL>", "");
		detailMessage = detailMessage.replace("?", "");
		
		ObjectMapper mapper = new ObjectMapper();
		CreditTransferResponse response = mapper.readValue(detailMessage.toString(), CreditTransferResponse.class);
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
