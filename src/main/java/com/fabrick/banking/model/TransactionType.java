package com.fabrick.banking.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class TransactionType implements Serializable
{
	private static final long serialVersionUID = -3271279011484411266L;
	
	private String enumeration;
	private String value;
}
