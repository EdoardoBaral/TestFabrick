package com.fabrick.banking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account implements Serializable
{
	private static final long serialVersionUID = 2734585296343914610L;
	
	private String accountCode;
	private String bicCode;
}
