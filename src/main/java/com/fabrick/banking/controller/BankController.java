package com.fabrick.banking.controller;

import com.fabrick.banking.dto.*;
import com.fabrick.banking.factory.ConverterFactory;
import com.fabrick.banking.interfaces.BalanceResponse;
import com.fabrick.banking.interfaces.CreditTransferRequest;
import com.fabrick.banking.interfaces.CreditTransferResponse;
import com.fabrick.banking.interfaces.TransactionsListResponse;
import com.fabrick.banking.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class BankController
{
	@Autowired
	private ConverterFactory factory;
	@Autowired
	private BankService service;
	
	@GetMapping(value = "/balance/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public BalanceResponse getBalance(@PathVariable(value = "accountId") Long accountId)
	{
		BalanceOutputDto outputDto = service.getBalance(accountId);
		BalanceResponse response = factory.toBalanceResponse(outputDto, accountId);
		
		return response;
	}
	
	@PostMapping(value = "/creditTransfer")
	public CreditTransferResponse payCreditTransfer(@PathVariable(value = "acountId") Long accountId, @RequestBody CreditTransferRequest request)
	{
		CreditTransferInputDto inputDto = factory.toCreditTransferInputDto(request, accountId);
		CreditTransferOutputDto outputDto = service.payCreditTransfer(inputDto);
		CreditTransferResponse response = factory.toCreditTransferResponse(outputDto);
		
		return response;
	}
	
	@GetMapping(value = "/transactionsList")
	public TransactionsListResponse getTransactionsList(@PathVariable(value = "accountId") Long accountId,
														@PathVariable(value = "fromAccountingDate") String fromAccountingDate,
														@PathVariable(value = "toAccountingDate") String toAccountingDate)
	{
		TransactionsListInputDto inputDto = factory.toTransactionsListInputDto(accountId, fromAccountingDate, toAccountingDate);
		TransactionsListOutputDto outputDto = service.getTransactionsList(inputDto);
		TransactionsListResponse response = factory.toTransactionsListResponse(outputDto);
		
		return response;
	}
}
