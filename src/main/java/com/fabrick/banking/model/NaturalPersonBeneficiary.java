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
public class NaturalPersonBeneficiary implements Serializable
{
	private static final long serialVersionUID = 4928344213511240876L;
	
	@NotNull(message = Constants.FISCAL_CODE_1_NULL_ERROR)
	@NotEmpty(message = Constants.FISCAL_CODE_1_EMPTY_ERROR)
	@NotBlank(message = Constants.FISCAL_CODE_1_BLANK_ERROR)
	private String fiscalCode1;
	private String fiscalCode2;
	private String fiscalCode3;
	private String fiscalCode4;
	private String fiscalCode5;
}
