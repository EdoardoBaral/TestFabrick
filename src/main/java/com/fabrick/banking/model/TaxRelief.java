package com.fabrick.banking.model;

import com.fabrick.banking.utils.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxRelief implements Serializable
{
	private static final long serialVersionUID = 9138712767156730158L;
	
	private String taxReliefId;
	@NotNull(message = Constants.IS_CONDO_UPGRADE_NULL_ERROR)
	private Boolean isCondoUpgrade;
	@NotNull(message = Constants.CREDITOR_FISCAL_CODE_NULL_ERROR)
	@NotEmpty(message = Constants.CREDITOR_FISCAL_CODE_EMPTY_ERROR)
	@NotBlank(message = Constants.CREDITOR_FISCAL_CODE_BLANK_ERROR)
	private String creditorFiscalCode;
	@Valid
	@NotNull(message = Constants.BENEFICIARY_TYPE_NULL_ERROR)
	private BeneficiaryType beneficiaryType;
	@Valid
	private NaturalPersonBeneficiary naturalPersonBeneficiary;
	private LegalPersonBeneficiary legalPersonBeneficiary;
}
