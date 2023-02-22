package com.fabrick.banking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LegalPersonBeneficiary implements Serializable
{
	private static final long serialVersionUID = -8887884380110301606L;
	
	private String fiscalCode;
	private String legalRepresentativeFiscalCode;
}
