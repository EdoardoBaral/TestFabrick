package com.fabrick.banking.interfaces;

import com.fabrick.banking.model.Creditor;
import com.fabrick.banking.model.TaxRelief;
import com.fabrick.banking.utils.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditTransferRequest implements Serializable
{
	private static final long serialVersionUID = 8351907055624144647L;
	
	@Valid
	@NotNull(message = Constants.CREDITOR_NULL_ERROR)
	private Creditor creditor;
	private String executionDate;
	private String uri;
	@NotNull(message = Constants.DESCRIPTION_NULL_ERROR)
	@NotEmpty(message = Constants.DESCRIPTION_EMPTY_ERROR)
	@NotBlank(message = Constants.DESCRIPTION_BLANK_ERROR)
	private String description;
	@NotNull(message = Constants.AMOUNT_NULL_ERROR)
	@Min(value = 1, message = Constants.AMOUNT_VALUE_ERROR)
	private BigDecimal amount;
	@NotNull(message = Constants.CURRENCY_NULL_ERROR)
	@NotEmpty(message = Constants.CURRENCY_EMPTY_ERROR)
	@NotBlank(message = Constants.CURRENCY_BLANK_ERROR)
	private String currency;
	private Boolean isUrgent;
	private Boolean isInstant;
	private String feeType;
	@NotNull(message = Constants.FEE_ACCOUNT_ID_NULL_ERROR)
	@NotEmpty(message = Constants.FEE_ACCOUNT_ID_EMPTY_ERROR)
	@NotBlank(message = Constants.FEE_ACCOUNT_ID_BLANK_ERROR)
	private String feeAccountId;
	@Valid
	private TaxRelief taxRelief;
}
