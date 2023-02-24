package com.fabrick.banking.service;

import com.fabrick.banking.dto.*;
import com.fabrick.banking.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class BankServiceImpl implements BankService
{
	@Autowired
	private RestTemplate restTemplate;
	
	public BalanceOutputDto getBalance(Long accountId)
	{
		String apiUrl = String.format(Constants.GET_BALANCE_URL, accountId);
		HttpEntity apiRequest = new HttpEntity(getHeaders());
		ResponseEntity<BalanceOutputDto> apiResponse = restTemplate.exchange(apiUrl, HttpMethod.GET, apiRequest, BalanceOutputDto.class);
		
		BalanceOutputDto outputDto = apiResponse.getBody();
		outputDto.setStatusCode(apiResponse.getStatusCode());
		
		return outputDto;
	}
	
	public CreditTransferOutputDto payCreditTransfer(CreditTransferInputDto inputDto)
	{
		String apiUrl = String.format(Constants.PAY_CREDIT_TRANSFER_URL, inputDto.getFeeAccountId());
		HttpEntity<CreditTransferInputDto> apiRequest = new HttpEntity<>(inputDto, getHeaders());
		ResponseEntity<CreditTransferOutputDto> apiResponse = restTemplate.exchange(apiUrl, HttpMethod.POST, apiRequest, CreditTransferOutputDto.class);
		
		CreditTransferOutputDto outputDto = apiResponse.getBody();
		outputDto.setStatusCode(apiResponse.getStatusCode());
		
		return outputDto;
	}
	
	public TransactionsListOutputDto getTransactionsList(TransactionsListInputDto inputDto)
	{
		String apiUrl = String.format(Constants.GET_TRANSACTIONS_URL, inputDto.getAccountId(), inputDto.getFromAccountingDate(), inputDto.getToAccountingDate());
		HttpEntity apiRequest = new HttpEntity(getHeaders());
		ResponseEntity<TransactionsListOutputDto> apiResponse = restTemplate.exchange(apiUrl, HttpMethod.GET, apiRequest, TransactionsListOutputDto.class);
		
		TransactionsListOutputDto outputDto = apiResponse.getBody();
		outputDto.setStatusCode(apiResponse.getStatusCode());
		
		return outputDto;
	}
	
	private HttpHeaders getHeaders()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Auth-Schema", Constants.AUTH_SCHEMA);
		headers.add("Api-Key", Constants.API_KEY);
		
		return headers;
	}
}
