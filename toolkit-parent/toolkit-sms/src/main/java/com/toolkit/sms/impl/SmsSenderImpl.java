package com.toolkit.sms.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.toolkit.sms.SmsSender;
import com.toolkit.sms.exceptions.UrlNotSetException;

import java.net.*;
import java.io.*;

/**
 * Used to send an sms using any HTTP based api.
 * 
 * @author Sameer Gauba
 *
 */
public class SmsSenderImpl implements SmsSender {

	private Map<String, String> requestParams = new LinkedHashMap<>();
	private String apiUrl;

	/**
	 * Used to send an sms using any HTTP based api.
	 * 
	 * @author Sameer Gauba
	 * @param apiUrl
	 *            : the api url of the website providing the sms service
	 */
	public SmsSenderImpl(String apiUrl) {
		super();
		this.apiUrl = apiUrl;
	}

	/**
	 * Used to send an sms using any HTTP based api.
	 * 
	 * @author Sameer Gauba
	 *
	 */
	public SmsSenderImpl() {
		super();
	}

	/**
	 * To use this method the apiUrl and requestParams fields of this class must
	 * be set. apiUrl should have the api url of the website providing the sms
	 * service and requestParams, which is a HashMap, should have the request
	 * parameters accepted by the api of the website.
	 *
	 * @author Sameer Gauba
	 * @return the response returned by the sms server
	 * @throws UrlNotSetException
	 * 
	 */
	@Override
	public String sendMsg() throws IOException, UrlNotSetException {
		if (apiUrl == null)
			throw new UrlNotSetException();
		String textUrl = apiUrl;
		textUrl += "?";

		Set<String> keys = requestParams.keySet();
		for (String key : keys) {
			String value = URLEncoder.encode(requestParams.get(key), "UTF-8");
			textUrl += (key + "=" + value + "&");
		}
		textUrl = textUrl.substring(0, textUrl.length() - 1);

		URL url = new URL(textUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.connect();

		BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String msgReturned;
		msgReturned = rd.readLine();
		rd.close();
		return msgReturned;

	}

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
	@Override
	public Map<String, String> getRequestParams() {
		return requestParams;
	}

	/**
	 * @author Sameer Gauba
	 * @return value of apiUrl field of SmsSender
	 */
	@Override
	public String getApiUrl() {
		return apiUrl;
	}

	/**
	 * Sets the apiUrl field of SmsSender
	 * 
	 * @author Sameer Gauba
	 * @param apiUrl
	 *            : the api url of the website providing the sms service
	 */
	@Override
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

}
