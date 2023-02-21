package com.fabrick.banking.utils;

public class Constants
{
	/* Constants for URL construnction */
	public static final String GET_BALANCE_URL = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/%s/balance";
	public static final String PAY_CREDIT_TRANSFER_URL = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/%s/payments/money-transfers/validate";
	public static final String GET_TRANSACTIONS_URL = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/%s/transactions?fromAccountingDate=%s&toAccountingDate=%s";
	
	/* Constants for error messages */
	public static final String ACCOUNTID_NULL_ERROR = "The accountId is null";
	public static final String ACCOUNTID_INVALID_ERROR = "The accountId %s is not valid";
	public static final String ACCOUNTID_NULL_EMPTY_ERROR = "The accountId is null or empty";
	public static final String CREDITOR_NAME_NULL_EMPTY_ERROR = "The creditor name is null or empty";
	public static final String DESCRIPTION_NULL_EMPTY_ERROR = "The description is null or empty";
	public static final String CURRENCY_NULL_EMPTY_ERROR = "The currency is null or empty";
	public static final String AMOUNT_INVALID_ERROR = "The amount %s is not valid";
	public static final String EXECUTION_DATE_NULL_EMPTY_ERROR = "The execution date is null or empty";
	public static final String FROM_DATE_NULL_MALFORMED_ERROR = "The argument fromAccountingDate %s is malformed or null";
	public static final String TO_DATE_NULL_MALFORMED_ERROR = "The argument toAccountingDate %s is malformed or null";
	
	/* Constants foh header parameters */
	public static final String AUTH_SCHEMA = "S2S";
	public static final String API_KEY = "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP";
}
