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
package com.ltu.secret.action.secret;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.JsonObject;
import com.ltu.secret.action.AbstractSecretAction;
import com.ltu.secret.configuration.ExceptionMessages;
import com.ltu.secret.dao.factory.DAOFactory;
import com.ltu.secret.exception.BadRequestException;
import com.ltu.secret.exception.DAOException;
import com.ltu.secret.exception.InternalErrorException;
import com.ltu.secret.model.action.secret.InsertSecretRequest;
import com.ltu.secret.model.action.secret.SecretResponse;
import com.ltu.secret.model.secret.Secret;
import com.ltu.secret.model.secret.SecretDAO;

/**
 * Action that creates a new Secret.
 *
 * @author uyphu
 * created on May 22, 2017
 */
public class InsertAction extends AbstractSecretAction {
	
	/** The logger. */
	private LambdaLogger logger;

    @Override
	public String handle(JsonObject request, Context lambdaContext) throws BadRequestException, InternalErrorException {
        logger = lambdaContext.getLogger();

        InsertSecretRequest input = getGson().fromJson(request, InsertSecretRequest.class);
        
        if (input == null ||
                input.getUserId() == null ||
                input.getUserId().trim().equals("")) {
            throw new BadRequestException(ExceptionMessages.EX_INVALID_INPUT);
        }

        if (input == null ||
                input.getDomain() == null ||
                input.getDomain().trim().equals("")) {
            throw new BadRequestException(ExceptionMessages.EX_INVALID_INPUT);
        }
        
        if (input == null ||
                input.getUsername() == null ||
                input.getUsername().trim().equals("")) {
            throw new BadRequestException(ExceptionMessages.EX_INVALID_INPUT);
        }
        
        if (input == null ||
                input.getPassword() == null ||
                input.getPassword().trim().equals("")) {
            throw new BadRequestException(ExceptionMessages.EX_INVALID_INPUT);
        }
        
        SecretDAO dao = DAOFactory.getSecretDAO();

        Secret newSecret = new Secret();
        newSecret.setUserId(input.getUserId());
        newSecret.setDomain(input.getDomain());
        newSecret.setUsername(input.getUsername());
        newSecret.setPassword(input.getPassword());
        newSecret.setSearchText(input.getDomain() + input.getUsername());
        newSecret.setNote(input.getNote());
        try {
        	newSecret = dao.insert(newSecret);
        } catch (final DAOException e) {
            logger.log("Error while creating new device\n" + e.getMessage());
            throw new InternalErrorException(ExceptionMessages.EX_DAO_ERROR);
        }

        if (newSecret.getId() == null || newSecret.getId().trim().equals("")) {
            logger.log("SecretID is null or empty");
            throw new InternalErrorException(ExceptionMessages.EX_DAO_ERROR);
        }

        SecretResponse output = new SecretResponse();
        output.setItem(newSecret);

        return getGson().toJson(output);
    }

}
