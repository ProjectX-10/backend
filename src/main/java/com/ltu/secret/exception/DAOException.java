package com.ltu.secret.exception;

/**
 * This exception should bot be exposed to Lambda or the client application. It's used internally to identify a DAO issue
 * @author Uy Phu
 * created on May 18, 2017
 */
public class DAOException extends Exception {
    
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2194070705225880244L;

	/**
     * Instantiates a new DAO exception.
     *
     * @param s the s
     * @param e the e
     */
    public DAOException(String s, Exception e) {
        super(s, e);
    }

    /**
     * Instantiates a new DAO exception.
     *
     * @param s the s
     */
    public DAOException(String s) {
        super(s);
    }
}
