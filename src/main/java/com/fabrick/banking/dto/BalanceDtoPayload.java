package com.fabrick.banking.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
public class BalanceDtoPayload implements Serializable
{
	private static final long serialVersionUID = -1747925874643053189L;
	
	private String date;
	private BigDecimal balance;
	private BigDecimal availableBalance;
	private String currency;
}
