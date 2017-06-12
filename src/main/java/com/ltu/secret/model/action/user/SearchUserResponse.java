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
package com.ltu.secret.model.action.user;

import java.util.List;

import com.ltu.secret.model.user.User;

/**
 * Bean for the pet creation response.
 * @author uyphu
 * created on May 21, 2017
 */
public class SearchUserResponse {
	
    /** The items. */
    private List<User> items;
    
    /** The next page token. */
    private String nextPageToken;

	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public List<User> getItems() {
		return items;
	}

	/**
	 * Sets the items.
	 *
	 * @param items the new items
	 */
	public void setItems(List<User> items) {
		this.items = items;
		if (items != null && !items.isEmpty()) {
			this.nextPageToken = items.get(items.size()-1).getId();
		}
	}

	/**
	 * Gets the next page token.
	 *
	 * @return the next page token
	 */
	public String getNextPageToken() {
		return nextPageToken;
	}

	/**
	 * Sets the next page token.
	 *
	 * @param nextPageToken the new next page token
	 */
	public void setNextPageToken(String nextPageToken) {
		this.nextPageToken = nextPageToken;
	}

}
