package com.fabrick.banking.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class TaxReliefDto implements Serializable
{
	private static final long serialVersionUID = -1104372204845351414L;
	
	private String taxReliefId;
	private Boolean isCondoUpgrade;
	private String creditorFiscalCode;
	private String beneficiaryType;
	private NaturalPersonBeneficiaryDto naturalPersonBeneficiary;
	private LegalPersonBeneficiaryDto legalPersonBeneficiary;
}
