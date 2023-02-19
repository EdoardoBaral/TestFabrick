package com.fabrick.banking.service;

import com.fabrick.banking.dto.*;
import com.fabrick.banking.interfaces.GenericResponse;
import com.fabrick.banking.model.ErrorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BankServiceImpl implements BankService
{
	@Autowired
	private RestTemplate restTemplate;
	
	public BalanceOutputDto getBalance(Long accountId)
	{
		try
		{
			validateGetBalanceInput(accountId);
		}
		catch(NullPointerException e)
		{
			return getBalanceOutputDtoGenericError(HttpStatus.BAD_REQUEST, "BAL001", e.getMessage());
		}
		catch(IllegalArgumentException e)
		{
			return getBalanceOutputDtoGenericError(HttpStatus.BAD_REQUEST, "BAL002", e.getMessage());
		}
		
		String apiUrl = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/"+ accountId +"/balance";
		HttpEntity apiRequest = new HttpEntity(getHeaders());
		ResponseEntity<BalanceOutputDto> apiResponse = restTemplate.exchange(apiUrl, HttpMethod.GET, apiRequest, BalanceOutputDto.class);
		
		BalanceOutputDto outputDto = apiResponse.getBody();
		outputDto.setStatusCode(apiResponse.getStatusCode());
		
		return outputDto;
	}
	
	private void validateGetBalanceInput(Long accountId) throws NullPointerException, IllegalArgumentException
	{
		if(accountId == null)
			throw new NullPointerException("The accountId is null");
		else if(accountId <= 0)
			throw new IllegalArgumentException("The accountId "+ accountId +" is not valid");
	}
	
	private BalanceOutputDto getBalanceOutputDtoGenericError(HttpStatus status, String code, String message)
	{
		BalanceOutputDto outputDto = new BalanceOutputDto();
		outputDto.setStatus(GenericResponse.STATUS_KO);
		outputDto.setStatusCode(status);
		
		List<ErrorModel> errorList = new ArrayList<>();
		ErrorModel error = new ErrorModel();
		error.setCode(code);
		error.setDescription(message);
		errorList.add(error);
		
		outputDto.setError(errorList);
		
		return outputDto;
	}
	
	private HttpHeaders getHeaders()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Auth-Schema", "S2S");
		headers.add("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");
		
		return headers;
	}
	
	public CreditTransferOutputDto payCreditTransfer(CreditTransferInputDto inputDto)
	{
		try
		{
			validatePayCreditTransferInput(inputDto);
		}
		catch(IllegalArgumentException e)
		{
			return getCreditTransferOutputDtoGenericError(HttpStatus.BAD_REQUEST, "CRT001", e.getMessage());
		}
		
		fillInputDto(inputDto);
		
		String apiUrl = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/"+ inputDto.getFeeAccountId() +"/payments/money-transfers/validate";
		HttpEntity<CreditTransferInputDto> apiRequest = new HttpEntity<>(inputDto, getHeaders());
		ResponseEntity<CreditTransferOutputDto> apiResponse = restTemplate.exchange(apiUrl, HttpMethod.POST, apiRequest, CreditTransferOutputDto.class);
		
		CreditTransferOutputDto outputDto = apiResponse.getBody();
		outputDto.setStatusCode(apiResponse.getStatusCode());
		
		return outputDto;
	}
	
	private void validatePayCreditTransferInput(CreditTransferInputDto inputDto) throws NullPointerException, IllegalArgumentException
	{
		if(inputDto.getFeeAccountId() == null || inputDto.getFeeAccountId().isEmpty())
			throw new IllegalArgumentException("The accountId is null or empty");
		else if(inputDto.getCreditor().getName() == null || inputDto.getCreditor().getName().isEmpty())
			throw new IllegalArgumentException("The creditor name is null or empty");
		else if(inputDto.getDescription() == null || inputDto.getDescription().isEmpty())
			throw new IllegalArgumentException("The description is null or empty");
		else if(inputDto.getCurrency() == null || inputDto.getCurrency().isEmpty())
			throw new IllegalArgumentException("The currency is null or empty");
		else if(inputDto.getAmount().doubleValue() <= 0)
			throw new IllegalArgumentException("The amount "+ inputDto.getAmount().doubleValue() +" is not valid");
		else if(inputDto.getExecutionDate() == null || inputDto.getExecutionDate().isEmpty())
			throw new IllegalArgumentException("The execution date is null or empty");
	}
	
	private CreditTransferOutputDto getCreditTransferOutputDtoGenericError(HttpStatus status, String code, String message)
	{
		CreditTransferOutputDto outputDto = new CreditTransferOutputDto();
		outputDto.setStatus(GenericResponse.STATUS_KO);
		outputDto.setStatusCode(status);
		
		List<ErrorModel> errorList = new ArrayList<>();
		ErrorModel error = new ErrorModel();
		error.setCode(code);
		error.setDescription(message);
		errorList.add(error);
		
		outputDto.setError(errorList);
		
		return outputDto;
	}
	
	/* Metodo per riempire i campi della response (oltre ai dati ricevuti in input all'API) per farla combaciare con quella d'esempio fornita nella documentazione */
	private void fillInputDto(CreditTransferInputDto inputDto)
	{
		AccountDto accountDto = new AccountDto();
		accountDto.setAccountCode("IT23A0336844430152923804660");
		accountDto.setBicCode("SELBIT2BXXX");
		inputDto.getCreditor().setAccount(accountDto);
		
		inputDto.getCreditor().setAddress(new AddressDto());
		
		inputDto.setUri("REMITTANCE_INFORMATION");
		inputDto.setIsUrgent(false);
		inputDto.setIsInstant(false);
		inputDto.setFeeType("SHA");
		
		TaxReliefDto taxReliefDto = new TaxReliefDto();
		taxReliefDto.setTaxReliefId("L449");
		taxReliefDto.setIsCondoUpgrade(false);
		taxReliefDto.setCreditorFiscalCode("56258745832");
		taxReliefDto.setBeneficiaryType("NATURAL_PERSON");
		
		NaturalPersonBeneficiaryDto naturalPersonBeneficiaryDto = new NaturalPersonBeneficiaryDto();
		naturalPersonBeneficiaryDto.setFiscalCode1("MRLFNC81L04A859L");
		taxReliefDto.setNaturalPersonBeneficiary(naturalPersonBeneficiaryDto);
		
		taxReliefDto.setLegalPersonBeneficiary(new LegalPersonBeneficiaryDto());
		
		inputDto.setTaxRelief(taxReliefDto);
	}
	
	public TransactionsListOutputDto getTransactionsList(TransactionsListInputDto inputDto)
	{
		try
		{
			validateGetTransactionsListInput(inputDto);
		}
		catch(IllegalArgumentException e)
		{
			return getTransactionsListOutputDtoGenericError(HttpStatus.BAD_REQUEST, "TRL001", e.getMessage());
		}
		
		String apiUrl = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/"+ inputDto.getAccountId() +"/transactions?fromAccountingDate="+ inputDto.getFromAccountingDate() +
							"&toAccountingDate="+ inputDto.getToAccountingDate();
		HttpEntity apiRequest = new HttpEntity(getHeaders());
		ResponseEntity<TransactionsListOutputDto> apiResponse = restTemplate.exchange(apiUrl, HttpMethod.GET, apiRequest, TransactionsListOutputDto.class);
		
		TransactionsListOutputDto outputDto = apiResponse.getBody();
		outputDto.setStatusCode(apiResponse.getStatusCode());
		
		return outputDto;
	}
	
	private void validateGetTransactionsListInput(TransactionsListInputDto inputDto) throws NullPointerException, IllegalArgumentException
	{
		if(inputDto.getAccountId() <= 0)
			throw new IllegalArgumentException("The accountId "+ inputDto.getAccountId() +" is not valid");
		else if (inputDto.getFromAccountingDate() == null || inputDto.getFromAccountingDate().length() != 10)
			throw new IllegalArgumentException("The argument fromAccountingDate "+ inputDto.getFromAccountingDate() +" is malformed or null");
		else if (inputDto.getToAccountingDate() == null || inputDto.getToAccountingDate().length() != 10)
			throw new IllegalArgumentException("The argument toAccountingDate "+ inputDto.getToAccountingDate() +" is malformed or null");
	}
	
	private TransactionsListOutputDto getTransactionsListOutputDtoGenericError(HttpStatus status, String code, String message)
	{
		TransactionsListOutputDto outputDto = new TransactionsListOutputDto();
		outputDto.setStatus(GenericResponse.STATUS_KO);
		outputDto.setStatusCode(status);
		
		List<ErrorModel> errorList = new ArrayList<>();
		ErrorModel error = new ErrorModel();
		error.setCode(code);
		error.setDescription(message);
		errorList.add(error);
		
		outputDto.setError(errorList);
		
		return outputDto;
	}
}
