package com.fabrick.banking.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class CreditTransferPayload implements Serializable
{
	private static final long serialVersionUID = 166864146661330201L;
	
	//Viene lasciato il payload vuoto perché non si ha un caso di test con esito OK
}
