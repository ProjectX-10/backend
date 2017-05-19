package com.ltu.secret.exception;

/**
 * This exception is thrown whenever an internal error occurs, for example a DAO error if the data store is not accessible.
 * The exception sets a default pattern in the string "INT_ERROR: .*" that can be easily matched from the API Gateway for
 * mapping.
 * @author Uy Phu
 * created on May 18, 2017
 */
public class InternalErrorException extends Exception {
    
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3760743545323488673L;
	/** The Constant PREFIX. */
    private static final String PREFIX = "INT_ERROR: ";

    /**
     * Instantiates a new internal error exception.
     *
     * @param s the s
     * @param e the e
     */
    public InternalErrorException(String s, Exception e) {
        super(PREFIX + s, e);
    }

    /**
     * Instantiates a new internal error exception.
     *
     * @param s the s
     */
    public InternalErrorException(String s) {
        super(PREFIX + s);
    }
    
}
