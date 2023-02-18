package com.fabrick.banking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentTransaction implements Serializable
{
	private static final long serialVersionUID = -2303053193229347818L;
	
	private String transactionId;
	private String operationId;
	private String accountingDate;
	private String valueDate;
	private TransactionType type;
	private BigDecimal amount;
	private String currency;
	private String description;
}
