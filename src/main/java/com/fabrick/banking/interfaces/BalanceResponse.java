package com.fabrick.banking.interfaces;

import com.fabrick.banking.model.BalancePayload;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BalanceResponse extends GenericResponse
{
	private static final long serialVersionUID = -5647408961577896162L;
	
	private BalancePayload payload;
	
	public BalanceResponse()
	{
		this.error = new ArrayList<>();
		this.errors = new ArrayList<>();
	}
}
