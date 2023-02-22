package com.fabrick.banking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address implements Serializable
{
	private static final long serialVersionUID = 4974024338983612368L;
	
	private String address;
	private String city;
	private String countryCode;
}
