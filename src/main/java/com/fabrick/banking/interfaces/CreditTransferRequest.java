package com.fabrick.banking.interfaces;

import com.fabrick.banking.model.Creditor;
import com.fabrick.banking.model.TaxRelief;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditTransferRequest implements Serializable
{
	private static final long serialVersionUID = 8351907055624144647L;
	
	private Creditor creditor;
	private String executionDate;
	private String uri;
	private String description;
	private BigDecimal amount;
	private String currency;
	private Boolean isUrgent;
	private Boolean isInstant;
	private String feeType;
	private String feeAccountId;
	private TaxRelief taxRelief;
}
