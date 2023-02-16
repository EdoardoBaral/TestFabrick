package com.fabrick.banking.dto;

import com.fabrick.banking.model.ErrorModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class CreditTransferOutputDto implements Serializable
{
	private static final long serialVersionUID = 1168900495697691201L;
	
	private String status;
	
	//Vengono dichiarate due liste error ed errors perché nelle API da testare compaiono con nomi differenti, senza uniformità.
	//Le due liste sono mutuamente esclusive, ne viene popolata solo una per volta
	private List<ErrorModel> error;
	private List<ErrorModel> errors;
	
	private CreditTransferDtoPayload payload;
}
