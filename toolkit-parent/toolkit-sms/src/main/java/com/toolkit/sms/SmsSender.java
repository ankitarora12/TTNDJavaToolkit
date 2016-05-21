package com.toolkit.sms;

import java.io.IOException;
import java.util.Map;

import com.toolkit.sms.exceptions.UrlNotSetException;

/**
 * Used to send an sms using any HTTP based api.
 * 
 * @author Sameer Gauba
 *
 */
public interface SmsSender {

	/**
	 * To use this method the apiUrl and requestParams fields of this class must
	 * be set. apiUrl should have the api url of the website providing the sms
	 * service and requestParams, which is a HashMap should have the request
	 * parameter accepted by the api of the website.
	 *
	 * @author Sameer Gauba
	 * @return the response returned by the sms server
	 * @throws UrlNotSetException 
	 * 
	 */
	String sendMsg() throws IOException, UrlNotSetException;

	/**
	 * 
	 * The values in this map are required in order to send an sms. The keys
	 * should have the name of the fields in the API of the sms provider and
	 * values should have the values of the respective fields.
	 * 
	 * @author Sameer Gauba
	 * @return returns an object of HashMap class, in which keys and values
	 *         should be given by user to send a sms
	 * 
	 */
	Map<String, String> getRequestParams();

	/**
	 * @author Sameer Gauba
	 * @return value of apiUrl field of SmsSender
	 */
	String getApiUrl();

	/**
	 * Sets the apiUrl field of SmsSender
	 * 
	 * @author Sameer Gauba
	 * @param apiUrl
	 *            : the api url of the website providing the sms service
	 */
	void setApiUrl(String apiUrl);

}