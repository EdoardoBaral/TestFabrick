package com.fabrick.banking.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
public class CreditTransferInputDto implements Serializable
{
	private static final long serialVersionUID = -7638543034864814117L;
	
	private CreditorDto creditor;
	private String executionDate;
	private String uri;
	private String description;
	private BigDecimal amount;
	private String currency;
	private Boolean isUrgent;
	private Boolean isInstant;
	private String feeType;
	private String feeAccountId;
	private TaxReliefDto taxRelief;
}
