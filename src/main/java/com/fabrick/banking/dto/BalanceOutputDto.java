package com.fabrick.banking.dto;

import com.fabrick.banking.model.ErrorModel;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class BalanceOutputDto implements Serializable
{
	private static final long serialVersionUID = -582232030450227409L;
	
	private String status;
	private HttpStatus statusCode;
	
	//Vengono dichiarate due liste error ed errors perché nelle API da testare compaiono con nomi differenti, senza uniformità.
	//Le due liste sono mutuamente esclusive, ne viene popolata solo una per volta
	private List<ErrorModel> error;
	private List<ErrorModel> errors;
	
	private BalanceDtoPayload payload;
}
