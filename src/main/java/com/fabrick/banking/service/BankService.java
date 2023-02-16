package com.fabrick.banking.service;

import com.fabrick.banking.dto.*;

public interface BankService
{
	BalanceOutputDto getBalance(Long accountId);
	CreditTransferOutputDto payCreditTransfer(CreditTransferInputDto inputDto);
	TransactionsListOutputDto getTransactionsList(TransactionsListInputDto inputDto);
}
