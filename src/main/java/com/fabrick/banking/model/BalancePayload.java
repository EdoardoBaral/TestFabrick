package com.fabrick.banking.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
public class BalancePayload implements Serializable
{
	private static final long serialVersionUID = -5071507385583051108L;
	
	private String date;
	private BigDecimal balance;
	private BigDecimal availableBalance;
	private String currency;
	private Long accountId;
}
