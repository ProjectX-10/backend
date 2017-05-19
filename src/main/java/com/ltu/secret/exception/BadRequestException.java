package com.ltu.secret.exception;

// TODO: Auto-generated Javadoc
/**
 * This exception should be thrown whenever requests fail validation. The exception sets a default pattern in the string
 * "BAD_REQ: .*" that can be easily matched from the API Gateway for mapping.
 * @author Uy Phu
 * created on May 18, 2017
 */
public class BadRequestException extends Exception {
  
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3428755124639467178L;
	
	/** The Constant PREFIX. */
	private static final String PREFIX = "BAD_REQ: ";
    
    /**
     * Instantiates a new bad request exception.
     *
     * @param s the s
     * @param e the e
     */
    public BadRequestException(String s, Exception e) {
        super(PREFIX + s, e);
    }

    /**
     * Instantiates a new bad request exception.
     *
     * @param s the s
     */
    public BadRequestException(String s) {
        super(PREFIX + s);
    }
    
}
