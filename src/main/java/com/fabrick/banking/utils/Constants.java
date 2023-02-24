package com.fabrick.banking.utils;

public class Constants
{
	/* Constants for URL construnction */
	public static final String GET_BALANCE_URL = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/%s/balance";
	public static final String PAY_CREDIT_TRANSFER_URL = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/%s/payments/money-transfers/validate";
	public static final String GET_TRANSACTIONS_URL = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/%s/transactions?fromAccountingDate=%s&toAccountingDate=%s";
	
	/* Constants for error messages */
	public static final String ACCOUNTID_MINVALUE_ERROR = "The accountId value must be greater or equals than 1";
	public static final String CREDITOR_NULL_ERROR = "The object creditor can't be null";
	public static final String CREDITOR_NAME_NULL_ERROR = "The creditor name is null";
	public static final String CREDITOR_NAME_EMPTY_ERROR = "The creditor name can't be empty";
	public static final String CREDITOR_NAME_BLANK_ERROR = "The creditor name can't be blank";
	public static final String CREDITOR_ACCOUNT_NULL_ERROR = "The creditor account can't be null";
	public static final String CREDITOR_ACCOUNT_CODE_NULL_ERROR = "The creditor.accountCode can't be null";
	public static final String CREDITOR_ACCOUNT_CODE_EMPTY_ERROR = "The creditor.accountCode can't be empty";
	public static final String CREDITOR_ACCOUNT_CODE_BLANK_ERROR = "The creditor.accountCode can't be blank";
	public static final String DESCRIPTION_NULL_ERROR = "The description can't be null";
	public static final String DESCRIPTION_EMPTY_ERROR = "The description can't be empty";
	public static final String DESCRIPTION_BLANK_ERROR = "The description can't be blank";
	public static final String AMOUNT_NULL_ERROR = "The amount can't be null";
	public static final String AMOUNT_VALUE_ERROR = "The amount's value must be greater or equals than 1";
	public static final String CURRENCY_NULL_ERROR = "The currency can't be null";
	public static final String CURRENCY_EMPTY_ERROR = "The currency can't be empty";
	public static final String CURRENCY_BLANK_ERROR = "The currency can't be blank";
	public static final String FEE_ACCOUNT_ID_NULL_ERROR = "The feeAccountId can't be null";
	public static final String FEE_ACCOUNT_ID_EMPTY_ERROR = "The feeAccountId can't be empty";
	public static final String FEE_ACCOUNT_ID_BLANK_ERROR = "The feeAccountId can't be blank";
	public static  final String IS_CONDO_UPGRADE_NULL_ERROR = "The taxRelief.isCondoUpgrade field can't be null";
	public static final String CREDITOR_FISCAL_CODE_NULL_ERROR = "The taxRelief.creditorFiscalCode can't be null";
	public static final String CREDITOR_FISCAL_CODE_EMPTY_ERROR = "The taxRelief.creditorFiscalCode can't be empty";
	public static final String CREDITOR_FISCAL_CODE_BLANK_ERROR = "The taxRelief.creditorFiscalCode can't be blank";
	public static final String BENEFICIARY_TYPE_NULL_ERROR = "The taxRelief.beneficiaryType can't be blank";
	public static final String FISCAL_CODE_1_NULL_ERROR = "The taxRelief.naturalPersonBeneficiary.fiscalCode1 can't be null";
	public static final String FISCAL_CODE_1_EMPTY_ERROR = "The taxRelief.naturalPersonBeneficiary.fiscalCode1 can't be empty";
	public static final String FISCAL_CODE_1_BLANK_ERROR = "The taxRelief.naturalPersonBeneficiary.fiscalCode1 can't be blank";
	public static final String FROM_ACCOUNTNG_DATE_SIZE_ERROR = "The argument fromAccountingDate's lenght must be 10 characters";
	public static final String TO_ACCOUNTNG_DATE_SIZE_ERROR = "The argument toAccountingDate's lenght must be 10 characters";
	
	/* Constants foh header parameters */
	public static final String AUTH_SCHEMA = "S2S";
	public static final String API_KEY = "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP";
}
