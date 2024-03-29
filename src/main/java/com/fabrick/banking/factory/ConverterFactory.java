package com.fabrick.banking.factory;

import com.fabrick.banking.dto.*;
import com.fabrick.banking.interfaces.*;
import com.fabrick.banking.model.BalancePayload;
import com.fabrick.banking.model.CreditTransferPayload;
import org.dozer.DozerBeanMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ConverterFactory
{
	public BalanceResponse toBalanceResponse(BalanceOutputDto outputDto, Long accountId)
	{
		BalanceResponse response = new BalanceResponse();
		response.setStatus(outputDto.getStatus());
		response.setStatusCode(GenericResponse.STATUS_OK.equals(outputDto.getStatus()) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
		
		if(GenericResponse.STATUS_KO.equals(outputDto.getStatus()))
		{
			response.setError(outputDto.getError());
			response.setErrors(outputDto.getErrors());
		}
		
		response.setPayload(mappingBalancePayload(outputDto.getPayload(), accountId));
		
		return response;
	}
	
	private BalancePayload mappingBalancePayload(BalanceDtoPayload balanceDtoPayload, Long accountId)
	{
		BalancePayload payload = new BalancePayload();
		payload.setDate(balanceDtoPayload.getDate());
		payload.setBalance(balanceDtoPayload.getBalance());
		payload.setAvailableBalance(balanceDtoPayload.getAvailableBalance());
		payload.setCurrency(balanceDtoPayload.getCurrency());
		payload.setAccountId(accountId);
		
		return payload;
	}
	
	public CreditTransferInputDto toCreditTransferInputDto(CreditTransferRequest request)
	{
		DozerBeanMapper mapper = new DozerBeanMapper();
		
		return mapper.map(request, CreditTransferInputDto.class);
	}
	
	public CreditTransferResponse toCreditTransferResponse(CreditTransferOutputDto outputDto)
	{
		CreditTransferResponse response = new CreditTransferResponse();
		response.setStatus(outputDto.getStatus());
		response.setStatusCode(GenericResponse.STATUS_OK.equals(outputDto.getStatus()) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
		
		if(GenericResponse.STATUS_KO.equals(outputDto.getStatus()))
		{
			response.setError(outputDto.getError());
			response.setErrors(outputDto.getErrors());
		}
		
		response.setPayload(mappingCreditTransferPayload(outputDto.getPayload()));
		
		return response;
	}
	
	private CreditTransferPayload mappingCreditTransferPayload(CreditTransferDtoPayload creditTransferDtoPayload)
	{
		//Viene predisposto il metodo ma viene restituito un payload vuoto perché non si ha un caso di test con esito positivo
		return new CreditTransferPayload();
	}
	
	public TransactionsListInputDto toTransactionsListInputDto(Long accountId, String fromAccountingDate, String toAccountingDate)
	{
		TransactionsListInputDto inputDto = new TransactionsListInputDto();
		inputDto.setAccountId(accountId);
		inputDto.setFromAccountingDate(fromAccountingDate);
		inputDto.setToAccountingDate(toAccountingDate);
		
		return inputDto;
	}
	
	public TransactionsListResponse toTransactionsListResponse(TransactionsListOutputDto outputDto)
	{
		TransactionsListResponse response = new TransactionsListResponse();
		response.setStatus(outputDto.getStatus());
		response.setStatusCode(GenericResponse.STATUS_OK.equals(outputDto.getStatus()) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
		
		if(GenericResponse.STATUS_KO.equals(outputDto.getStatus()))
		{
			response.setError(outputDto.getError());
			response.setErrors(outputDto.getErrors());
		}
		
		response.setPayload(outputDto.getPayload());
		
		return response;
	}
}
