package com.fabrick.banking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionType implements Serializable
{
	private static final long serialVersionUID = -3271279011484411266L;
	
	private String enumeration;
	private String value;
}
