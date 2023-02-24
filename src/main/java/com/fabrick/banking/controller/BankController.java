package com.fabrick.banking.controller;

import com.fabrick.banking.dto.*;
import com.fabrick.banking.factory.ConverterFactory;
import com.fabrick.banking.interfaces.BalanceResponse;
import com.fabrick.banking.interfaces.CreditTransferRequest;
import com.fabrick.banking.interfaces.CreditTransferResponse;
import com.fabrick.banking.interfaces.TransactionsListResponse;
import com.fabrick.banking.service.BankService;
import com.fabrick.banking.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import javax.validation.constraints.*;

@RestController
@Validated
@RequestMapping("/api")
public class BankController
{
	@Autowired
	private ConverterFactory factory;
	@Autowired
	private BankService service;
	
	@GetMapping(value = "/balance/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BalanceResponse> getBalance(@PathVariable(value = "accountId")
														  @Min(value = 1, message = Constants.ACCOUNTID_MINVALUE_ERROR)
														  Long accountId)
	{
		BalanceOutputDto outputDto = service.getBalance(accountId);
		BalanceResponse response = factory.toBalanceResponse(outputDto, accountId);
			
		return ResponseEntity.ok(response);
	}
	
	@PostMapping(value = "/creditTransfer", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CreditTransferResponse> payCreditTransfer(@Valid @RequestBody CreditTransferRequest request)
	{
		CreditTransferInputDto inputDto = factory.toCreditTransferInputDto(request);
		CreditTransferOutputDto outputDto = service.payCreditTransfer(inputDto);
		CreditTransferResponse response = factory.toCreditTransferResponse(outputDto);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/transactionsList/{accountId}/{fromAccountingDate}/{toAccountingDate}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TransactionsListResponse> getTransactionsList(@PathVariable(value = "accountId")
																			@Min(value = 1, message = Constants.ACCOUNTID_MINVALUE_ERROR)
																			Long accountId,
																		@PathVariable(value = "fromAccountingDate")
																			@Size(min = 10, max = 10, message = Constants.FROM_ACCOUNTNG_DATE_SIZE_ERROR)
																			String fromAccountingDate,
																		@PathVariable(value = "toAccountingDate")
																			@Size(min = 10, max = 10, message = Constants.TO_ACCOUNTNG_DATE_SIZE_ERROR)
																			String toAccountingDate)
	{
		TransactionsListInputDto inputDto = factory.toTransactionsListInputDto(accountId, fromAccountingDate, toAccountingDate);
		TransactionsListOutputDto outputDto = service.getTransactionsList(inputDto);
		TransactionsListResponse response = factory.toTransactionsListResponse(outputDto);
		
		return ResponseEntity.ok(response);
	}
}
