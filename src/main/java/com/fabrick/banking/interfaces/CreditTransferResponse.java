package com.fabrick.banking.interfaces;

import com.fabrick.banking.model.CreditTransferPayload;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditTransferResponse extends GenericResponse
{
	private static final long serialVersionUID = 6959229086816147533L;
	
	private CreditTransferPayload payload;
}
