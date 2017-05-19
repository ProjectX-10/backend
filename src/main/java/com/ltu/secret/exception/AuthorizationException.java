package com.ltu.secret.exception;

/**
 * This exception is thrown whenever this service is not authorize to communicate with another AWS service, it should not
 * be exposed to Lambda or returned to the client. When this exception is caught we should throw an InternalErrorException
 * @author Uy Phu
 * created on May 18, 2017
 */
public class AuthorizationException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4703169830516186002L;

	/**
	 * Instantiates a new authorization exception.
	 *
	 * @param s the s
	 * @param e the e
	 */
	public AuthorizationException(String s, Exception e) {
        super(s, e);
    }

    /**
     * Instantiates a new authorization exception.
     *
     * @param s the s
     */
    public AuthorizationException(String s) {
        super(s);
    }
    
}
