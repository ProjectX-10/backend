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
 * Configuration parameters for the Cognito credentials provider.
 *
 * @author uy phu
 * created on May 19, 2017
 */
public class CognitoConfiguration {
    
    /** The Constant IDENTITY_POOL_ID. */
    // TODO: Specify the identity pool id
    public static final String IDENTITY_POOL_ID = "us-east-1:efa41743-090e-4485-bdcf-8c1e17513107";
    
    /** The Constant CUSTOM_PROVIDER_NAME. */
    // TODO: Specify the custom provider name used by the identity pool
    public static final String CUSTOM_PROVIDER_NAME = "com.ltu.secret";

    /** The Constant COGNITO_PROVIDER_NAME. */
    // This should not be changed, it is a default value for Cognito.
    public static final String COGNITO_PROVIDER_NAME = "cognito-identity.amazonaws.com";
}
