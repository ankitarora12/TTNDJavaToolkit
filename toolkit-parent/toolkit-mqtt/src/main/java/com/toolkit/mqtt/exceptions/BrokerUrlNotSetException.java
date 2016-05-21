package com.toolkit.mqtt.exceptions;


/**
 * Thrown when brokerUrl field of class MqttMessageClient is not set.
 * @author Sameer Gauba
 *
 */
public class BrokerUrlNotSetException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Thrown when brokerUrl field of class MqttMessageClient is not set.
	 * @author Sameer Gauba
	 *
	 */
	public BrokerUrlNotSetException() {
		//Default constructor
	}

	/**
	 * Thrown when brokerUrl field of class MqttMessageClient is not set.
	 * @author Sameer Gauba
	 *
	 */
	public BrokerUrlNotSetException(String message) {
		super(message);
	}

	/**
	 * Thrown when brokerUrl field of class MqttMessageClient is not set.
	 * @author Sameer Gauba
	 *
	 */
	public BrokerUrlNotSetException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Thrown when brokerUrl field of class MqttMessageClient is not set.
	 * @author Sameer Gauba
	 *
	 */
	public BrokerUrlNotSetException(String message, Throwable cause) {
		super(message, cause);
	}

	public BrokerUrlNotSetException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
