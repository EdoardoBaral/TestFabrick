package com.fabrick.banking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NaturalPersonBeneficiaryDto implements Serializable
{
	private static final long serialVersionUID = -3820039157247198760L;
	
	private String fiscalCode1;
	private String fiscalCode2;
	private String fiscalCode3;
	private String fiscalCode4;
	private String fiscalCode5;
}
