package com.fabrick.banking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionsListPayload implements Serializable
{
	private static final long serialVersionUID = 6348407430130793745L;
	
	private List<PaymentTransaction> list;
}
