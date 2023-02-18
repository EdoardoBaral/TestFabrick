package com.fabrick.banking.service;

import com.fabrick.banking.controller.AbstractTest;
import com.fabrick.banking.controller.BankController;
import com.fabrick.banking.factory.ConverterFactory;
import com.fabrick.banking.interfaces.BalanceResponse;
import com.fabrick.banking.interfaces.CreditTransferRequest;
import com.fabrick.banking.interfaces.CreditTransferResponse;
import com.fabrick.banking.interfaces.TransactionsListResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {BankController.class, BankServiceImpl.class, ConverterFactory.class})
public class BankControllerTest extends AbstractTest
{
	@Test
	public void getBalanceTest() throws Exception
	{
		String uri = "/api/balance/14537780";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
											  .get(uri)
											  .accept(MediaType.APPLICATION_JSON_VALUE))
								  .andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		String content = mvcResult.getResponse().getContentAsString();
		BalanceResponse response = super.mapFromJson(content, BalanceResponse.class);
		assertNotNull(response);
	}
	
	@Test
	public void payCreditTransferTest() throws Exception
	{
		String uri = "/api/creditTransfer";
		CreditTransferRequest request = initCreditTransferRequest();
		String inputJson = mapToJson(request);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
											  .post(uri)
											  .contentType(MediaType.APPLICATION_JSON_VALUE)
											  .content(inputJson))
								  .andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		String content = mvcResult.getResponse().getContentAsString();
		CreditTransferResponse response = super.mapFromJson(content, CreditTransferResponse.class);
		assertNotNull(response);
	}
	
	private CreditTransferRequest initCreditTransferRequest()
	{
		CreditTransferRequest request = new CreditTransferRequest();
		request.setReceiverName("John Doe");
		request.setDescription("Payment invoice 75-2017");
		request.setCurrency("EUR");
		request.setAmount(new BigDecimal(800));
		request.setExecutionDate("2019-04-01");
		request.setAccountId(14537780L);
		
		return request;
	}
	
	@Test
	public void getTransactionListTest() throws Exception
	{
		String uri = "/api/transactionsList/14537780/2019-01-01/2019-12-31";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
											  .get(uri)
											  .accept(MediaType.APPLICATION_JSON_VALUE))
								  .andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		String content = mvcResult.getResponse().getContentAsString();
		TransactionsListResponse response = super.mapFromJson(content, TransactionsListResponse.class);
		assertNotNull(response);
	}
}
