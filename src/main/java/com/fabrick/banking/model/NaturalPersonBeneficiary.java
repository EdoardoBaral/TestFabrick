package com.fabrick.banking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NaturalPersonBeneficiary implements Serializable
{
	private static final long serialVersionUID = 4928344213511240876L;
	
	private String fiscalCode1;
	private String fiscalCode2;
	private String fiscalCode3;
	private String fiscalCode4;
	private String fiscalCode5;
}
