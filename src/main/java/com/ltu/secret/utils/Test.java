/**
 * 
 */
package com.ltu.secret.utils;

import java.util.Calendar;

import org.json.JSONObject;

import com.ltu.secret.model.action.secret.SearchSecretResponse;

import pl.zientarski.SchemaMapper;


/**
 * @author PhuLTU
 *
 */
public class Test {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Json schema...");
		
		JSONObject schema = new SchemaMapper().toJsonSchema4(SearchSecretResponse.class, true);
		System.out.println(schema.toString());
		
		System.out.println(Calendar.getInstance().getTime().getTime());
		
		//DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		
	}

}

