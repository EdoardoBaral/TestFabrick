package com.fabrick.banking.service;

import com.fabrick.banking.dto.*;
import com.fabrick.banking.interfaces.GenericResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {BankService.class, BankServiceImpl.class, RestTemplate.class})
public class BankServiceImplIntegrationTest
{
	@Autowired
	private BankService service;
	
	@Test
	@Disabled //Test con chiamata reale all'API di recupero saldo di un rapporto di Banca Sella, disabilitato per prevenire possibili errori durante la build di Maven
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
		assertEquals(getExampleBalanceDtoPayload(), outputDto.getPayload());
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
	@Disabled //Test con chiamata reale all'API di recupero saldo di un rapporto di Banca Sella, disabilitato per prevenire possibili errori durante la build di Maven
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
	@Disabled //Test con chiamata reale all'API di recupero saldo di un rapporto di Banca Sella, disabilitato per prevenire possibili errori durante la build di Maven
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
}