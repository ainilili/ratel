package org.nico.ratel.landlords.exception;

public class LandlordException extends RuntimeException {

	private static final long serialVersionUID = -5643145833569293539L;

	public LandlordException() {
		super();
	}

	public LandlordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LandlordException(String message, Throwable cause) {
		super(message, cause);
	}

	public LandlordException(String message) {
		super(message);
	}

	public LandlordException(Throwable cause) {
		super(cause);
	}

}
