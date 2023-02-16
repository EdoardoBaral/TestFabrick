package com.fabrick.banking.dto;

import com.fabrick.banking.model.ErrorModel;
import com.fabrick.banking.model.TransactionsListPayload;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class TransactionsListOutputDto implements Serializable
{
	private static final long serialVersionUID = -5929486288667648583L;
	
	private String status;
	private HttpStatus statusCode;
	
	//Vengono dichiarate due liste error ed errors perché nelle API da testare compaiono con nomi differenti, senza uniformità.
	//Le due liste sono mutuamente esclusive, ne viene popolata solo una per volta
	private List<ErrorModel> error;
	private List<ErrorModel> errors;
	
	private TransactionsListPayload payload;
}
