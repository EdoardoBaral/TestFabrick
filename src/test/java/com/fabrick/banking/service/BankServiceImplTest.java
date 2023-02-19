package com.fabrick.banking.service;

import com.fabrick.banking.dto.*;
import com.fabrick.banking.interfaces.GenericResponse;
import com.fabrick.banking.model.PaymentTransaction;
import com.fabrick.banking.model.TransactionType;
import com.fabrick.banking.model.TransactionsListPayload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {BankService.class, BankServiceImpl.class, RestTemplate.class})
public class BankServiceImplTest
{
	@Autowired
	private BankService service;
	
	@MockBean
	private RestTemplate restTemplate;
	
	@Test
	public void getBalanceTestOK() throws Exception
	{
		Long accountId = 14537780L;
		
		ResponseEntity<BalanceOutputDto> balanceResponseMocked = ResponseEntity.ok(getBalanceOutputDtoMocked());
		when(restTemplate.exchange(anyString(), any(), any(), eq(BalanceOutputDto.class))).thenReturn(balanceResponseMocked);
		
		BalanceOutputDto outputDto = service.getBalance(accountId);
		
		assertNotNull(outputDto);
		assertEquals(GenericResponse.STATUS_OK, outputDto.getStatus());
		assertEquals(HttpStatus.OK, outputDto.getStatusCode());
		assertTrue(outputDto.getError().isEmpty());
		assertNull(outputDto.getErrors());
		assertNotNull(outputDto.getPayload());
		assertEquals(getExampleBalanceDtoPayload(), outputDto.getPayload());
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
		when(restTemplate.exchange(anyString(), any(), any(), eq(CreditTransferOutputDto.class))).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
		
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
		
		ResponseEntity<TransactionsListOutputDto> transactionsListResponseMocked = ResponseEntity.ok(getTransactionsListOutputDtoMocked());
		when(restTemplate.exchange(anyString(), any(), any(), eq(TransactionsListOutputDto.class))).thenReturn(transactionsListResponseMocked);
		
		TransactionsListOutputDto outputDto = service.getTransactionsList(inputDto);
		
		assertNotNull(outputDto);
		assertEquals("OK", outputDto.getStatus());
		assertEquals(HttpStatus.OK, outputDto.getStatusCode());
		assertTrue(outputDto.getError().isEmpty());
		assertNull(outputDto.getErrors());
		assertEquals(1, outputDto.getPayload().getList().size());
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
