package com.fabrick.banking.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class CreditTransferDtoPayload implements Serializable
{
	private static final long serialVersionUID = -7003302233307653676L;
	
	//Viene lasciato il payload vuoto perché non si ha un caso di test con esito OK
}
