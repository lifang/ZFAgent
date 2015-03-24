package com.comdosoft.financial.user.utils.Exception;

@SuppressWarnings("serial")
public class AccountRepetitionException extends RuntimeException {
	public AccountRepetitionException(String msg) {
		super(msg);
	}
}
