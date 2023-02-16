package com.fabrick.banking.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class LegalPersonBeneficiaryDto implements Serializable
{
	private static final long serialVersionUID = -9196169404061271198L;
	
	private String fiscalCode;
	private String legalRepresentativeFiscalCode;
}
