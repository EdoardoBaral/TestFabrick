package com.fabrick.banking.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class TransactionsListInputDto implements Serializable
{
	private static final long serialVersionUID = 8001109897483016215L;
	
	private Long accountId;
	private String fromAccountingDate;
	private String toAccountingDate;
}
