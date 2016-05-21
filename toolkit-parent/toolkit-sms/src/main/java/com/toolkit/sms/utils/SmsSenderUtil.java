package com.toolkit.sms.utils;

import com.toolkit.sms.SmsSender;
import com.toolkit.sms.impl.SmsSenderImpl;

/**
 * 
 * Provides utility methods to support SmsSender interface
 * 
 * @author Sameer Gauba
 *
 */
public class SmsSenderUtil {

	private SmsSenderUtil() {
	}

	/**
	 * To get a new object of {@link SmsSenderImpl} which is the implementation
	 * of {@link SmsSender} interface
	 * 
	 * @return SmsSender object
	 */

	public static SmsSender getSmsSender() {

		return new SmsSenderImpl();

	}

	/**
	 * To get a new object of {@link SmsSenderImpl} which is the implementation
	 * of {@link SmsSender} interface
	 * 
	 * @param apiUrl
	 *            : the api url of the website providing the sms service
	 * @return SmsSender object
	 */
	public static SmsSender getSmsSender(String apiUrl) {

		return new SmsSenderImpl(apiUrl);

	}

}
