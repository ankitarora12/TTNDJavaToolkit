/**
 * 
 */
package com.toolkit.mqtt.exceptions;

/**
 * Thrown when MqttClient object is not connected to the broker url by the
 * object of {@link MqttMessageClientImpl}
 * 
 * @author Sameer Gauba
 */
public class NoClientSetException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Thrown when MqttClient object is not connected to the broker url by the
	 * object of {@link MqttMessageClientImpl}
	 * 
	 * @author Sameer Gauba
	 */
	public NoClientSetException() {
		//Default constructor
	}

	/**
	 * Thrown when MqttClient object is not connected to the broker url by the
	 * object of {@link MqttMessageClientImpl}
	 * 
	 * @author Sameer Gauba
	 * @param message
	 */
	public NoClientSetException(String message) {
		super(message);
	}

	/**
	 * Thrown when MqttClient object is not connected to the broker url by the
	 * object of {@link MqttMessageClientImpl}
	 * 
	 * @author Sameer Gauba
	 * @param cause
	 */
	public NoClientSetException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 * Thrown when MqttClient object is not connected to the broker url by the
	 * object of {@link MqttMessageClientImpl}
	 * 
	 * @author Sameer Gauba
	 * @param message
	 * @param cause
	 */
	public NoClientSetException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 * Thrown when MqttClient object is not connected to the broker url by the
	 * object of {@link MqttMessageClientImpl}
	 * 
	 * @author Sameer Gauba
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NoClientSetException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
