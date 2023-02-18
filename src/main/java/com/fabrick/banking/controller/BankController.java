package com.fabrick.banking.controller;

import com.fabrick.banking.dto.*;
import com.fabrick.banking.factory.ConverterFactory;
import com.fabrick.banking.interfaces.BalanceResponse;
import com.fabrick.banking.interfaces.CreditTransferRequest;
import com.fabrick.banking.interfaces.CreditTransferResponse;
import com.fabrick.banking.interfaces.TransactionsListResponse;
import com.fabrick.banking.service.BankService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api")
public class BankController
{
	@Autowired
	private ConverterFactory factory;
	@Autowired
	private BankService service;
	
	@GetMapping(value = "/balance/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public BalanceResponse getBalance(@PathVariable(value = "accountId") Long accountId)
	{
		try
		{
			BalanceOutputDto outputDto = service.getBalance(accountId);
			BalanceResponse response = factory.toBalanceResponse(outputDto, accountId);
			
			return response;
		}
		catch(HttpClientErrorException e)
		{
			return manageBalanceResponseError(e);
		}
	}
	
	private BalanceResponse manageBalanceResponseError(HttpClientErrorException e)
	{
		ObjectMapper mapper = new ObjectMapper();
		HttpStatus statusCode = e.getStatusCode();
		
		String jsonResponse = e.getMessage().replace("<EOL>", "");
		jsonResponse = jsonResponse.substring(jsonResponse.indexOf('{'));
		
		BalanceResponse response;
		try
		{
			response = mapper.readValue(jsonResponse, BalanceResponse.class);
			response.setStatusCode(statusCode);
		}
		catch(JsonProcessingException ex)
		{
			response = new BalanceResponse();
		}
		
		return response;
	}
	
	@PostMapping(value = "/creditTransfer", produces = MediaType.APPLICATION_JSON_VALUE)
	public CreditTransferResponse payCreditTransfer(@RequestBody CreditTransferRequest request)
	{
		try
		{
			CreditTransferInputDto inputDto = factory.toCreditTransferInputDto(request);
			CreditTransferOutputDto outputDto = service.payCreditTransfer(inputDto);
			CreditTransferResponse response = factory.toCreditTransferResponse(outputDto);
			
			return response;
		}
		catch(HttpClientErrorException e)
		{
			return manageCreditTransferResponseError(e);
		}
	}
	
	private CreditTransferResponse manageCreditTransferResponseError(HttpClientErrorException e)
	{
		ObjectMapper mapper = new ObjectMapper();
		HttpStatus statusCode = e.getStatusCode();
		
		String jsonResponse = e.getMessage().replace("<EOL>", "");
		jsonResponse = jsonResponse.substring(jsonResponse.indexOf('{'));
		
		CreditTransferResponse response;
		try
		{
			response = mapper.readValue(jsonResponse, CreditTransferResponse.class);
			response.setStatusCode(statusCode);
		}
		catch(JsonProcessingException ex)
		{
			response = new CreditTransferResponse();
		}
		
		return response;
	}
	
	@GetMapping(value = "/transactionsList/{accountId}/{fromAccountingDate}/{toAccountingDate}", produces = MediaType.APPLICATION_JSON_VALUE)
	public TransactionsListResponse getTransactionsList(@PathVariable(value = "accountId") Long accountId,
														@PathVariable(value = "fromAccountingDate") String fromAccountingDate,
														@PathVariable(value = "toAccountingDate") String toAccountingDate)
	{
		try
		{
			TransactionsListInputDto inputDto = factory.toTransactionsListInputDto(accountId, fromAccountingDate, toAccountingDate);
			TransactionsListOutputDto outputDto = service.getTransactionsList(inputDto);
			TransactionsListResponse response = factory.toTransactionsListResponse(outputDto);
			
			return response;
		}
		catch(HttpClientErrorException e)
		{
			return manageTransactionsListResponseError(e);
		}
	}
	
	private TransactionsListResponse manageTransactionsListResponseError(HttpClientErrorException e)
	{
		ObjectMapper mapper = new ObjectMapper();
		HttpStatus statusCode = e.getStatusCode();
		
		String jsonResponse = e.getMessage().replace("<EOL>", "");
		jsonResponse = jsonResponse.substring(jsonResponse.indexOf('{'));
		
		TransactionsListResponse response;
		try
		{
			response = mapper.readValue(jsonResponse, TransactionsListResponse.class);
			response.setStatusCode(statusCode);
		}
		catch(JsonProcessingException ex)
		{
			response = new TransactionsListResponse();
		}
		
		return response;
	}
}
