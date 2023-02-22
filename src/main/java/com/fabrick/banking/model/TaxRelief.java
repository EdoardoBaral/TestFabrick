package com.fabrick.banking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxRelief implements Serializable
{
	private static final long serialVersionUID = 9138712767156730158L;
	
	private String taxReliefId;
	private Boolean isCondoUpgrade;
	private String creditorFiscalCode;
	private String beneficiaryType;
	private NaturalPersonBeneficiary naturalPersonBeneficiary;
	private LegalPersonBeneficiary legalPersonBeneficiary;
}
