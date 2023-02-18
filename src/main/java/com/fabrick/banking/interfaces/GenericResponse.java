package com.fabrick.banking.interfaces;

import com.fabrick.banking.model.ErrorModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class GenericResponse implements Serializable
{
	private static final long serialVersionUID = -8447842011638282804L;
	
	public static final String STATUS_OK = "OK";
	public static final String STATUS_KO = "KO";
	
	protected String status;
	protected HttpStatus statusCode;
	
	//Vengono dichiarate due liste error ed errors perché nelle API da testare compaiono con nomi differenti, senza uniformità.
	//Le due liste sono mutuamente esclusive, ne viene popolata solo una per volta
	protected List<ErrorModel> error;
	protected List<ErrorModel> errors;
}
