package com.fabrick.banking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorModel implements Serializable
{
	private static final long serialVersionUID = 7127045998001630420L;
	
	private String code;
	private String description;
	private String params;
}
