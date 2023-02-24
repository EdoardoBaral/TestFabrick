package com.fabrick.banking.model;

import com.fabrick.banking.utils.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Creditor implements Serializable
{
	private static final long serialVersionUID = 8177886151411943703L;
	
	@NotNull(message = Constants.CREDITOR_NAME_NULL_ERROR)
	@NotEmpty(message = Constants.CREDITOR_NAME_EMPTY_ERROR)
	@NotBlank(message = Constants.CREDITOR_NAME_BLANK_ERROR)
	private String name;
	@Valid
	@NotNull(message = Constants.CREDITOR_ACCOUNT_NULL_ERROR)
	private Account account;
	private Address address;
}
