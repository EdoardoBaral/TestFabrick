package com.fabrick.banking.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class AddressDto implements Serializable
{
	private static final long serialVersionUID = 2485648111603445184L;
	
	private String address;
	private String city;
	private String countryCode;
}
