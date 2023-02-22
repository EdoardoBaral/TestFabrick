package com.fabrick.banking.controller;

import com.fabrick.banking.dto.*;
import com.fabrick.banking.factory.ConverterFactory;
import com.fabrick.banking.interfaces.*;
import com.fabrick.banking.model.*;
import com.fabrick.banking.service.BankService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BankControllerTest extends AbstractTest
{
	@MockBean
	private BankService service;
	@Autowired
	private ConverterFactory factory;
	
	@Test
	public void getBalanceTest() throws Exception
	{
		String uri = "/api/balance/14537780";
		BalanceOutputDto outputDtoMocked = getBalanceOutputDtoMocked();
		when(service.getBalance(anyLong())).thenReturn(outputDtoMocked);
		
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
	
	private BalanceOutputDto getBalanceOutputDtoMocked()
	{
		BalanceOutputDto outputDto = new BalanceOutputDto();
		outputDto.setStatus(GenericResponse.STATUS_OK);
		outputDto.setStatusCode(HttpStatus.OK);
		outputDto.setError(new ArrayList<>());
		outputDto.setPayload(getExampleBalanceDtoPayload());
		
		return outputDto;
	}
	
	private BalanceDtoPayload getExampleBalanceDtoPayload()
	{
		BalanceDtoPayload example = new BalanceDtoPayload();
		example.setDate("2023-02-17");
		example.setBalance(new BigDecimal("300022.11"));
		example.setAvailableBalance(new BigDecimal("300022.11"));
		example.setCurrency("EUR");
		
		return example;
	}
	
	@Test
	public void payCreditTransferTest() throws Exception
	{
		String uri = "/api/creditTransfer";
		CreditTransferRequest request = initCreditTransferRequest();
		String inputJson = mapToJson(request);
		CreditTransferOutputDto outputDtoMocked = getCreditTransferOutputDtoMocked();
		when(service.payCreditTransfer(any())).thenReturn(outputDtoMocked);
		
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
	
	private CreditTransferOutputDto getCreditTransferOutputDtoMocked()
	{
		CreditTransferOutputDto outputDto = new CreditTransferOutputDto();
		outputDto.setStatus(GenericResponse.STATUS_OK);
		outputDto.setStatusCode(HttpStatus.OK);
		outputDto.setPayload(new CreditTransferDtoPayload());
		
		return outputDto;
	}
	
	private CreditTransferRequest initCreditTransferRequest()
	{
		CreditTransferRequest request = new CreditTransferRequest();
		request.setCreditor(getCreditor());
		request.setExecutionDate("2019-04-01");
		request.setUri("REMITTANCE_INFORMATION");
		request.setDescription("Payment invoice 75/2017");
		request.setAmount(new BigDecimal(800));
		request.setCurrency("EUR");
		request.setIsUrgent(false);
		request.setIsInstant(false);
		request.setFeeType("SHA");
		request.setFeeAccountId("14537780");
		request.setTaxRelief(getTaxRelief());
		
		return request;
	}
	
	private Creditor getCreditor()
	{
		Creditor creditor = new Creditor();
		creditor.setName("John Doe");
		creditor.setAccount(getAccount());
		creditor.setAddress(new Address());
		
		return creditor;
	}
	
	private Account getAccount()
	{
		Account account = new Account();
		account.setAccountCode("IT23A0336844430152923804660");
		account.setBicCode("SELBIT2BXXX");
		
		return account;
	}
	
	private TaxRelief getTaxRelief()
	{
		TaxRelief taxRelief = new TaxRelief();
		taxRelief.setTaxReliefId("L449");
		taxRelief.setIsCondoUpgrade(false);
		taxRelief.setCreditorFiscalCode("56258745832");
		taxRelief.setBeneficiaryType("NATURAL_PERSON");
		taxRelief.setNaturalPersonBeneficiary(getNaturalPersonBeneficiary());
		taxRelief.setLegalPersonBeneficiary(new LegalPersonBeneficiary());
		
		return taxRelief;
	}
	
	private NaturalPersonBeneficiary getNaturalPersonBeneficiary()
	{
		NaturalPersonBeneficiary naturalPerson = new NaturalPersonBeneficiary();
		naturalPerson.setFiscalCode1("MRLFNC81L04A859L");
		
		return naturalPerson;
	}
	
	@Test
	public void getTransactionListTest() throws Exception
	{
		String uri = "/api/transactionsList/14537780/2019-01-01/2019-12-31";
		TransactionsListOutputDto outputDtoMocked = getTransactionsListOutputDtoMocked();
		when(service.getTransactionsList(any())).thenReturn(outputDtoMocked);
		
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
	
	private TransactionsListOutputDto getTransactionsListOutputDtoMocked()
	{
		TransactionsListOutputDto outputDto = new TransactionsListOutputDto();
		outputDto.setStatus("OK");
		outputDto.setStatusCode(HttpStatus.OK);
		outputDto.setError(new ArrayList<>());
		outputDto.setPayload(getTransactionsListPayloadMock());
		
		return outputDto;
	}
	
	private TransactionsListPayload getTransactionsListPayloadMock()
	{
		TransactionsListPayload payload = new TransactionsListPayload();
		
		List<PaymentTransaction> list = new ArrayList<>();
		PaymentTransaction transaction = new PaymentTransaction();
		transaction.setTransactionId("123");
		transaction.setOperationId("ABC-123");
		transaction.setAccountingDate("2023-02-20");
		transaction.setValueDate("2023-02-20");
		
		TransactionType type = new TransactionType();
		type.setEnumeration("Enumeration");
		type.setValue("Value");
		transaction.setType(type);
		
		transaction.setAmount(new BigDecimal(10));
		transaction.setCurrency("EUR");
		transaction.setDescription("Description");
		
		list.add(transaction);
		payload.setList(list);
		
		return payload;
	}
}
