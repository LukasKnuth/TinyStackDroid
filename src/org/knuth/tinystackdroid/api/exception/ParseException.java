package org.knuth.tinystackdroid.api.exception;

/**
 * Thrown if an API-Response couldn't be parsed.
 * @author Lukas Knuth
 *
 */
public class ParseException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ParseException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}
