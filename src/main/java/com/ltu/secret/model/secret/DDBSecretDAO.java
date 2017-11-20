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
package com.ltu.secret.model.secret;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.ltu.secret.configuration.DynamoDBConfiguration;
import com.ltu.secret.configuration.ExceptionMessages;
import com.ltu.secret.constants.Constants;
import com.ltu.secret.exception.DAOException;
import com.ltu.secret.exception.ErrorCodeDetail;
import com.ltu.secret.utils.AppUtil;
import com.ltu.secret.utils.ConvertUtil;

/**
 * DynamoDB implementation of the SecretDAO interface. This class reads the configuration from the DyanmoDBConfiguration
 * object in the com.amazonaws.apigatewaydemo.configuration package. Credentials to access DynamoDB are retrieved from
 * the Lambda environment.
 * <p/>
 * The table in DynamoDB should be created with an Hash Key called secretname.
 * @author uy phu
 * created on May 19, 2017
 */
public class DDBSecretDAO extends com.ltu.secret.dao.AbstractDao<Secret> implements SecretDAO {
	
	private static DDBSecretDAO instance = null;
    
	/**
	 * Returns an initialized instance of the DDBSecretDAO object. DAO objects
	 * should be retrieved through the DAOFactory class
	 *
	 * @return An initialized instance of the DDBSecretDAO object
	 */
	public static DDBSecretDAO getInstance() {
		if (instance == null) {
			instance = new DDBSecretDAO();
		}

		return instance;
	}

	protected DDBSecretDAO() {
		super(Secret.class);
	}

	/**
	 * Inserts a new row in the DynamoDB secrets table.
	 *
	 * @param secret
	 *            The new secret information
	 * @return The secretname that was just inserted in DynamoDB
	 * @throws DAOException
	 */
	@Override
	public String createSecret(Secret secret) throws DAOException {
		if (secret.getUserId() == null || secret.getUserId().trim().equals("")) {
			throw new DAOException("Cannot create secret with empty secretname");
		}

		return insert(secret).getId();
	}

	@Override
	public Secret update(Secret secret) throws DAOException {
		if (secret.getId() == null) {
			throw new DAOException(ExceptionMessages.EX_INVALID_INPUT);
		}
		return super.save(secret);
	}

	@Override
	public Secret merge(Secret secret) throws DAOException {
		if (secret.getId() != null) {
			return update(secret);
		} else {
			return insert(secret);
		}
	}

	@Override
	public void delete(String id) throws DAOException {
		Secret item = get(id);
		if (item == null) {
			throw new DAOException(ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
		} else {
			super.delete(item);
		}
	}

	@Override
	public Secret find(String id) {
		return super.find(id);
	}

	@Override
	public List<Secret> search(String query, int limit, String cursor) {
		if (query == null) {
			return mapperScan(query, limit, cursor);
		}
		return scan(query, limit, cursor);
	}

	private Map<String, AttributeValue> buildExclusiveStartKey(String cursor) {
		if (cursor == null || cursor.trim().equals(Constants.EMPTY_STRING)) {
			return null;
		}
		Map<String, AttributeValue> exclusiveStartKey = new HashMap<String, AttributeValue>();
		exclusiveStartKey.put("id", new AttributeValue(cursor));
		return exclusiveStartKey;
	}

	private ScanRequest buildScan(String query, int limit) {
		ScanRequest scanRequest = new ScanRequest(DynamoDBConfiguration.SECRET_TABLE_NAME);

		if (query != null) {
			HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
			String[] fields = query.split(Constants.AND_STRING);
			for (String field : fields) {
				if (field.indexOf("userId:") != -1) {
					String[] array = field.split(":");
					Condition condition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
							.withAttributeValueList(new AttributeValue().withS(array[1]));
					scanFilter.put("userId", condition);
				} else if (field.indexOf("domain:") != -1) {
					String[] array = field.split(":");
					Condition condition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
							.withAttributeValueList(new AttributeValue().withS(array[1]));
					scanFilter.put("domain", condition);
				} else if (field.indexOf("username:") != -1) {
					String[] array = field.split(":");
					Condition condition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
							.withAttributeValueList(new AttributeValue().withS(array[1]));
					scanFilter.put("username", condition);
				} 
			}

			scanRequest.withScanFilter(scanFilter);
		}
		// if (limit <= 0 || limit > DynamoDBConfiguration.SCAN_LIMIT) {
		// limit = DynamoDBConfiguration.SCAN_LIMIT;
		// }
		// scanRequest.setLimit(limit);

		scanRequest.setLimit(DynamoDBConfiguration.SCAN_LIMIT);

		return scanRequest;
	}

	private DynamoDBScanExpression buildScanMapper(String query, int limit) {
		DynamoDBScanExpression dbScanExpression = new DynamoDBScanExpression();

		if (query != null) {
			String[] fields = query.split(Constants.AND_STRING);
			for (String field : fields) {
				if (field.indexOf("userId:") != -1) {
					String[] array = field.split(":");
					Condition condition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
							.withAttributeValueList(new AttributeValue().withS(array[1]));
					dbScanExpression.addFilterCondition("userId", condition);
				} else if (field.indexOf("domain:") != -1) {
					String[] array = field.split(":");
					Condition condition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
							.withAttributeValueList(new AttributeValue().withS(array[1]));
					dbScanExpression.addFilterCondition("domain", condition);
				} else if (field.indexOf("username:") != -1) {
					String[] array = field.split(":");
					Condition condition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
							.withAttributeValueList(new AttributeValue().withS(array[1]));
					dbScanExpression.addFilterCondition("username", condition);
				} 
			}
		}
		// if (limit <= 0 || limit > DynamoDBConfiguration.SCAN_LIMIT) {
		// limit = DynamoDBConfiguration.SCAN_LIMIT;
		// }
		// dbScanExpression.setLimit(limit);

		dbScanExpression.setLimit(DynamoDBConfiguration.SCAN_LIMIT);

		return dbScanExpression;
	}

	@Override
	public List<Secret> scan(String query, int limit, String cursor) {
		ScanRequest scanRequest = buildScan(query, limit);
		Map<String, AttributeValue> exclusiveStartKey = buildExclusiveStartKey(cursor);
		List<Secret> secrets = new ArrayList<Secret>();

		do {
			if (exclusiveStartKey != null) {
				scanRequest.setExclusiveStartKey(exclusiveStartKey);
			}
			ScanResult scanResult = client.scan(scanRequest);

			if (scanResult != null && scanResult.getItems().size() > 0) {
				for (Map<String, AttributeValue> item : scanResult.getItems()) {
					secrets.add(ConvertUtil.toSecret(item));
					if (limit == secrets.size()) {
						return secrets;
					}
				}
			}
			exclusiveStartKey = scanResult.getLastEvaluatedKey();
		} while (exclusiveStartKey != null);

		return secrets;
	}

	@Override
	public List<Secret> mapperScan(String query, int limit, String cursor) {
		DynamoDBScanExpression scanExpression = buildScanMapper(query, limit);
		Map<String, AttributeValue> exclusiveStartKey = buildExclusiveStartKey(cursor);
		if (exclusiveStartKey != null) {
			scanExpression.setExclusiveStartKey(exclusiveStartKey);
		}
		PaginatedScanList<Secret> scanList = getMapper().scan(Secret.class, scanExpression);
		return scanList;
	}

	/**
	 * Gets the mapper.
	 *
	 * @return the mapper
	 */
	protected DynamoDBMapper getMapper() {
		return new DynamoDBMapper(client);
	}

	@Override
	public Secret insert(Secret secret) throws DAOException {
		Secret item = find(secret.getUserId(), secret.getDomain(), secret.getUsername());
		if (item != null) {
			throw new DAOException(ExceptionMessages.EX_DUPLICATED_ITEM);
		}
		secret.setCreatedAt(AppUtil.getUTCDateTime());
		return super.save(secret);
	}

	@Override
	public Secret find(String userId, String domain, String username) {
		StringBuilder query = new StringBuilder();
		query.append("userId:" + userId);
		query.append(Constants.AND_STRING);
		query.append("domain:" + domain);
		query.append(Constants.AND_STRING);
		query.append("username:" + username);
		
		List<Secret> secrets = search(query.toString(), 1, null);
		if (secrets != null && !secrets.isEmpty()) {
			return secrets.get(0);
		}
		return null;
	}
}
