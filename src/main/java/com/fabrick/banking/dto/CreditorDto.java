package com.fabrick.banking.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class CreditorDto implements Serializable
{
	private static final long serialVersionUID = -8458524255305822242L;
	
	private String name;
	private AccountDto account;
	private AddressDto address;
}
