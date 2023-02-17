package com.fabrick.banking.service;

import com.fabrick.banking.dto.*;
import com.fabrick.banking.interfaces.GenericResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {BankService.class, BankServiceImpl.class})
public class BankServiceIntegrationTest
{
	@Autowired
	private BankService service;
	
	@Test
	public void getBalanceTestOK()
	{
		Long accountId = 14537780L;
		BalanceOutputDto outputDto = service.getBalance(accountId);
		
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_OK, outputDto.getStatus());
		assertEquals(HttpStatus.OK, outputDto.getStatusCode());
		assertTrue(outputDto.getError().isEmpty());
		assertNull(outputDto.getErrors());
		assertNotNull(outputDto.getPayload());
		assertEquals(getExampleBalanceOutputDto(), outputDto.getPayload());
	}
	
	private BalanceDtoPayload getExampleBalanceOutputDto()
	{
		BalanceDtoPayload example = new BalanceDtoPayload();
		example.setDate("2023-02-17");
		example.setBalance(new BigDecimal("300022.11"));
		example.setAvailableBalance(new BigDecimal("300022.11"));
		example.setCurrency("EUR");
		
		return example;
	}
	
	@Test
	public void getBalanceTestKOAccountIdNull()
	{
		BalanceOutputDto outputDto = service.getBalance(null);
		
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_KO, outputDto.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST, outputDto.getStatusCode());
		assertEquals(1, outputDto.getError().size());
		assertEquals("BAL001", outputDto.getError().get(0).getCode());
		assertEquals("The accountId is null", outputDto.getError().get(0).getDescription());
		assertNull(outputDto.getError().get(0).getParams());
	}
	
	@Test
	public void getBalanceTestKOInvalidAccountId()
	{
		BalanceOutputDto outputDto = service.getBalance(-1L);
		
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_KO, outputDto.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST, outputDto.getStatusCode());
		assertEquals(1, outputDto.getError().size());
		assertEquals("BAL002", outputDto.getError().get(0).getCode());
		assertEquals("The accountId -1 is not valid", outputDto.getError().get(0).getDescription());
		assertNull(outputDto.getError().get(0).getParams());
	}
	
	@Test
	public void payCreditTransferTestKO()
	{
		CreditTransferInputDto inputDto = initCreditTransferInputDtoOK();
		assertThrows(HttpClientErrorException.class, () -> service.payCreditTransfer(inputDto));
	}
	
	private CreditTransferInputDto initCreditTransferInputDtoOK()
	{
		CreditTransferInputDto inputDto = new CreditTransferInputDto();
		
		CreditorDto creditorDto = new CreditorDto();
		creditorDto.setName("John Doe");
		inputDto.setCreditor(creditorDto);
		
		inputDto.setExecutionDate("2019-04-01");
		inputDto.setDescription("Payment invoice 75/2017");
		inputDto.setAmount(new BigDecimal(800));
		inputDto.setCurrency("EUR");
		inputDto.setFeeAccountId("14537780");
		
		return inputDto;
	}
	
	@Test
	public void payCreditTransferTestKOAccountId()
	{
		CreditTransferInputDto inputDto = initCreditTransferInputDtoOK();
		inputDto.setFeeAccountId(null);
		CreditTransferOutputDto outputDto = service.payCreditTransfer(inputDto);
		
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_KO, outputDto.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST, outputDto.getStatusCode());
		assertEquals(1, outputDto.getError().size());
		assertEquals("CRT001", outputDto.getError().get(0).getCode());
		assertEquals("The accountId is null or empty", outputDto.getError().get(0).getDescription());
		assertNull(outputDto.getError().get(0).getParams());
		
		inputDto.setFeeAccountId("");
		outputDto = service.payCreditTransfer(inputDto);
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_KO, outputDto.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST, outputDto.getStatusCode());
		assertEquals(1, outputDto.getError().size());
		assertEquals("CRT001", outputDto.getError().get(0).getCode());
		assertEquals("The accountId is null or empty", outputDto.getError().get(0).getDescription());
		assertNull(outputDto.getError().get(0).getParams());
	}
	
	@Test
	public void payCreditTransferTestKOCreditorName()
	{
		CreditTransferInputDto inputDto = initCreditTransferInputDtoOK();
		inputDto.getCreditor().setName(null);
		CreditTransferOutputDto outputDto = service.payCreditTransfer(inputDto);
		
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_KO, outputDto.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST, outputDto.getStatusCode());
		assertEquals(1, outputDto.getError().size());
		assertEquals("CRT001", outputDto.getError().get(0).getCode());
		assertEquals("The creditor name is null or empty", outputDto.getError().get(0).getDescription());
		assertNull(outputDto.getError().get(0).getParams());
		
		inputDto.getCreditor().setName("");
		outputDto = service.payCreditTransfer(inputDto);
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_KO, outputDto.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST, outputDto.getStatusCode());
		assertEquals(1, outputDto.getError().size());
		assertEquals("CRT001", outputDto.getError().get(0).getCode());
		assertEquals("The creditor name is null or empty", outputDto.getError().get(0).getDescription());
		assertNull(outputDto.getError().get(0).getParams());
	}
	
	@Test
	public void payCreditTransferTestKODescription()
	{
		CreditTransferInputDto inputDto = initCreditTransferInputDtoOK();
		inputDto.setDescription(null);
		CreditTransferOutputDto outputDto = service.payCreditTransfer(inputDto);
		
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_KO, outputDto.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST, outputDto.getStatusCode());
		assertEquals(1, outputDto.getError().size());
		assertEquals("CRT001", outputDto.getError().get(0).getCode());
		assertEquals("The description is null or empty", outputDto.getError().get(0).getDescription());
		assertNull(outputDto.getError().get(0).getParams());
		
		inputDto.setDescription("");
		outputDto = service.payCreditTransfer(inputDto);
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_KO, outputDto.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST, outputDto.getStatusCode());
		assertEquals(1, outputDto.getError().size());
		assertEquals("CRT001", outputDto.getError().get(0).getCode());
		assertEquals("The description is null or empty", outputDto.getError().get(0).getDescription());
		assertNull(outputDto.getError().get(0).getParams());
	}
	
	@Test
	public void payCreditTransferTestKOCurrency()
	{
		CreditTransferInputDto inputDto = initCreditTransferInputDtoOK();
		inputDto.setCurrency(null);
		CreditTransferOutputDto outputDto = service.payCreditTransfer(inputDto);
		
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_KO, outputDto.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST, outputDto.getStatusCode());
		assertEquals(1, outputDto.getError().size());
		assertEquals("CRT001", outputDto.getError().get(0).getCode());
		assertEquals("The currency is null or empty", outputDto.getError().get(0).getDescription());
		assertNull(outputDto.getError().get(0).getParams());
		
		inputDto.setCurrency("");
		outputDto = service.payCreditTransfer(inputDto);
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_KO, outputDto.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST, outputDto.getStatusCode());
		assertEquals(1, outputDto.getError().size());
		assertEquals("CRT001", outputDto.getError().get(0).getCode());
		assertEquals("The currency is null or empty", outputDto.getError().get(0).getDescription());
		assertNull(outputDto.getError().get(0).getParams());
	}
	
	@Test
	public void payCreditTransferTestKOAmount()
	{
		CreditTransferInputDto inputDto = initCreditTransferInputDtoOK();
		inputDto.setAmount(new BigDecimal(0));
		CreditTransferOutputDto outputDto = service.payCreditTransfer(inputDto);
		
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_KO, outputDto.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST, outputDto.getStatusCode());
		assertEquals(1, outputDto.getError().size());
		assertEquals("CRT001", outputDto.getError().get(0).getCode());
		assertEquals("The amount 0.0 is not valid", outputDto.getError().get(0).getDescription());
		assertNull(outputDto.getError().get(0).getParams());
	}
	
	@Test
	public void payCreditTransferTestKOExecutionDate()
	{
		CreditTransferInputDto inputDto = initCreditTransferInputDtoOK();
		inputDto.setExecutionDate(null);
		CreditTransferOutputDto outputDto = service.payCreditTransfer(inputDto);
		
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_KO, outputDto.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST, outputDto.getStatusCode());
		assertEquals(1, outputDto.getError().size());
		assertEquals("CRT001", outputDto.getError().get(0).getCode());
		assertEquals("The execution date is null or empty", outputDto.getError().get(0).getDescription());
		assertNull(outputDto.getError().get(0).getParams());
		
		inputDto.setExecutionDate("");
		outputDto = service.payCreditTransfer(inputDto);
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_KO, outputDto.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST, outputDto.getStatusCode());
		assertEquals(1, outputDto.getError().size());
		assertEquals("CRT001", outputDto.getError().get(0).getCode());
		assertEquals("The execution date is null or empty", outputDto.getError().get(0).getDescription());
		assertNull(outputDto.getError().get(0).getParams());
	}
	
	@Test
	public void getTransactionListTestOK()
	{
		TransactionsListInputDto inputDto = initTransactionsListInputDtoOK();
		TransactionsListOutputDto outputDto = service.getTransactionsList(inputDto);
		
		assertNotNull(outputDto);
		assertEquals("OK", outputDto.getStatus());
		assertEquals(HttpStatus.OK, outputDto.getStatusCode());
		assertTrue(outputDto.getError().isEmpty());
		assertNull(outputDto.getErrors());
		assertEquals(17, outputDto.getPayload().getList().size());
	}
	
	private TransactionsListInputDto initTransactionsListInputDtoOK()
	{
		TransactionsListInputDto inputDto = new TransactionsListInputDto();
		inputDto.setAccountId(14537780L);
		inputDto.setFromAccountingDate("2019-01-01");
		inputDto.setToAccountingDate("2019-12-31");
		
		return inputDto;
	}
	
	@Test
	public void getTransactionListTestKOAccountId()
	{
		TransactionsListInputDto inputDto = initTransactionsListInputDtoOK();
		inputDto.setAccountId(0L);
		TransactionsListOutputDto outputDto = service.getTransactionsList(inputDto);
		
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_KO, outputDto.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST, outputDto.getStatusCode());
		assertEquals(1, outputDto.getError().size());
		assertEquals("TRL001", outputDto.getError().get(0).getCode());
		assertEquals("The accountId 0 is not valid", outputDto.getError().get(0).getDescription());
		assertNull(outputDto.getError().get(0).getParams());
	}
	
	@Test
	public void getTransactionListTestKOFromDate()
	{
		TransactionsListInputDto inputDto = initTransactionsListInputDtoOK();
		inputDto.setFromAccountingDate(null);
		TransactionsListOutputDto outputDto = service.getTransactionsList(inputDto);
		
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_KO, outputDto.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST, outputDto.getStatusCode());
		assertEquals(1, outputDto.getError().size());
		assertEquals("TRL001", outputDto.getError().get(0).getCode());
		assertEquals("The argument fromAccountingDate "+ inputDto.getFromAccountingDate() +" is malformed or null", outputDto.getError().get(0).getDescription());
		assertNull(outputDto.getError().get(0).getParams());
		
		inputDto.setFromAccountingDate("23-01-01");
		outputDto = service.getTransactionsList(inputDto);
		
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_KO, outputDto.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST, outputDto.getStatusCode());
		assertEquals(1, outputDto.getError().size());
		assertEquals("TRL001", outputDto.getError().get(0).getCode());
		assertEquals("The argument fromAccountingDate "+ inputDto.getFromAccountingDate() +" is malformed or null", outputDto.getError().get(0).getDescription());
		assertNull(outputDto.getError().get(0).getParams());
	}
	
	@Test
	public void getTransactionListTestKOToDate()
	{
		TransactionsListInputDto inputDto = initTransactionsListInputDtoOK();
		inputDto.setToAccountingDate(null);
		TransactionsListOutputDto outputDto = service.getTransactionsList(inputDto);
		
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_KO, outputDto.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST, outputDto.getStatusCode());
		assertEquals(1, outputDto.getError().size());
		assertEquals("TRL001", outputDto.getError().get(0).getCode());
		assertEquals("The argument toAccountingDate "+ inputDto.getToAccountingDate() +" is malformed or null", outputDto.getError().get(0).getDescription());
		assertNull(outputDto.getError().get(0).getParams());
		
		inputDto.setToAccountingDate("23-01-01");
		outputDto = service.getTransactionsList(inputDto);
		
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_KO, outputDto.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST, outputDto.getStatusCode());
		assertEquals(1, outputDto.getError().size());
		assertEquals("TRL001", outputDto.getError().get(0).getCode());
		assertEquals("The argument toAccountingDate "+ inputDto.getToAccountingDate() +" is malformed or null", outputDto.getError().get(0).getDescription());
		assertNull(outputDto.getError().get(0).getParams());
	}
}