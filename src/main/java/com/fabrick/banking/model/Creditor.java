package com.fabrick.banking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Creditor implements Serializable
{
	private static final long serialVersionUID = 8177886151411943703L;
	
	private String name;
	private Account account;
	private Address address;
}
