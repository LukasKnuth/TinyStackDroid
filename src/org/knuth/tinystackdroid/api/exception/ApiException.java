package org.knuth.tinystackdroid.api.exception;

/**
 * Thrown when the SO-API couldn't be queried.
 * @author Lukas Knuth
 *
 */
public class ApiException extends Exception {

	private static final long serialVersionUID = 1L;

	public ApiException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
    
    public ApiException(String detailMessage) {
        super(detailMessage);
    }
}
