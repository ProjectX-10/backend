/*
 * Copyright 2017 ltu.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://ltu.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package com.ltu.secret.configuration;

// TODO: Auto-generated Javadoc
/**
 * Static list of error messages when exceptions are thrown.
 * @author uy phu
 * created on May 19, 2017
 */
public class ExceptionMessages {
    
    /** The Constant EX_INVALID_INPUT. */
    public static final String EX_INVALID_INPUT = "Invalid input parameters";
    
    /** The Constant EX_PWD_SALT. */
    public static final String EX_PWD_SALT = "Cannot generate password salt";
    
    /** The Constant EX_PWD_ENCRYPT. */
    public static final String EX_PWD_ENCRYPT = "Failed to encrypt password";
    
    /** The Constant EX_PWD_SAVE. */
    public static final String EX_PWD_SAVE = "Failed to save password";
    
    /** The Constant EX_NO_COGNITO_IDENTITY. */
    public static final String EX_NO_COGNITO_IDENTITY = "Cannot retrieve Cognito identity";
    
    /** The Constant EX_DAO_ERROR. */
    public static final String EX_DAO_ERROR = "Error loading user";
    
}
