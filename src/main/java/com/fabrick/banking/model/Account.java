package com.fabrick.banking.model;

import com.fabrick.banking.utils.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account implements Serializable
{
	private static final long serialVersionUID = 2734585296343914610L;
	
	@NotNull(message = Constants.CREDITOR_ACCOUNT_CODE_NULL_ERROR)
	@NotEmpty(message = Constants.CREDITOR_ACCOUNT_CODE_EMPTY_ERROR)
	@NotBlank(message = Constants.CREDITOR_ACCOUNT_CODE_BLANK_ERROR)
	private String accountCode;
	private String bicCode;
}
