package com.fabrick.banking.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class TransactionsListPayload implements Serializable
{
	private static final long serialVersionUID = 6348407430130793745L;
	
	private List<PaymentTransaction> list;
	
	public TransactionsListPayload()
	{
		list = new ArrayList<>();
	}
}
