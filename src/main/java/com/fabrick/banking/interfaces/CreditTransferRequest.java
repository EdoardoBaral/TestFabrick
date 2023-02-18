package com.fabrick.banking.interfaces;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
public class CreditTransferRequest implements Serializable
{
	private static final long serialVersionUID = 8351907055624144647L;
	
	private Long accountId;
	private String receiverName;
	private String description;
	private String currency;
	private BigDecimal amount;
	private String executionDate;
}
