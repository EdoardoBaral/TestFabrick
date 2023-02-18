package com.fabrick.banking.interfaces;

import com.fabrick.banking.model.TransactionsListPayload;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionsListResponse extends GenericResponse
{
	
	private static final long serialVersionUID = -9150042869707920999L;
	
	private TransactionsListPayload payload;
	
	public TransactionsListResponse()
	{
		this.error = new ArrayList<>();
		this.errors = new ArrayList<>();
	}
}
