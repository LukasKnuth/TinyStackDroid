package org.knuth.tinystackdroid.api.exception;

public class ParseException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ParseException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}
