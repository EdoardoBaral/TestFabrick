package com.fabrick.banking.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class AccountDto implements Serializable
{
	private static final long serialVersionUID = 8403379608032593279L;
	
	private String accountCode;
	private String bicCode;
}
