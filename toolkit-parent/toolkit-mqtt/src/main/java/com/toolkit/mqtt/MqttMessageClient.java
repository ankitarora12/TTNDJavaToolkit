package com.toolkit.mqtt;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;

import com.toolkit.mqtt.exceptions.BrokerUrlNotSetException;
import com.toolkit.mqtt.exceptions.NoClientSetException;

public interface MqttMessageClient {

	String getBrokerUrl();
	
	public String getDirectoryPath();

	/**
	 * Accepts a string that represents the url of the broker providing mqtt
	 * service
	 * 
	 * @author Sameer Gauba
	 * @param brokerUrl
	 */
	void setBrokerUrl(String brokerUrl);

	/**
	 * If this parameter is set, file-based persistent data store is created
	 * within the specified directory.
	 * 
	 * @author Sameer Gauba
	 * 
	 */
	public void setDirectoryPath(String directoryPath);

	String getUsername();

	/**
	 * @author Sameer Gauba This string represents the username to connect a
	 *         client to the brokerUrl
	 * @param username
	 */
	void setUsername(String username);

	String getPassword();

	/**
	 * This string represents the password to connect a client to the brokerUrl
	 * 
	 * @author Sameer Gauba
	 * @param password
	 */
	void setPassword(String password);

	/**
	 * Allows the user to connect to the broker through brokerUrl, username and
	 * password.
	 * 
	 * @author Sameer Gauba
	 *         <p>
	 * @param cleanSession
	 *            : The clean session flag indicates the broker, whether the
	 *            client wants to establish a persistent session or not. A
	 *            persistent session (cleanSession is false) means, that the
	 *            broker will store all subscriptions for the client and also
	 *            all missed messages, when subscribing with Quality of Service
	 *            (QoS) 1 or 2. If clean session is set to true, the broker
	 *            won’t store anything for the client and will also purge all
	 *            information from a previous persistent session.
	 *            </p>
	 * @param keepAliveInterval
	 *            : The keep alive is a time interval, the clients commits to by
	 *            sending regular PING Request messages to the broker. The
	 *            broker response with PING Response and this mechanism will
	 *            allow both sides to determine if the other one is still alive
	 *            and reachable.
	 * @throws MqttException
	 * @throws InterruptedException
	 * @throws BrokerUrlNotSetException
	 */
	void connectClient(Boolean cleanSession, Integer keepAliveInterval)
			throws MqttException, InterruptedException, BrokerUrlNotSetException;

	/**
	 * Used to unsubscribe from a topic with the given topicName
	 * 
	 * @param topicName
	 *            : Name of the topic which the user wants to unsubscribe.
	 * @throws MqttException
	 * @throws NoClientSetException
	 * @author Sameer Gauba
	 */
	void unSubscribeTopic(String topicName) throws MqttException, NoClientSetException;

	/**
	 * Used to publish a message on a given topic.
	 * 
	 * @param topicName
	 *            : Name of the topic where message is needed to be published.
	 * @param message
	 *            : The message which is needed to be published.
	 *            <p>
	 * @param publishQos
	 *            : Used to define the quality of service of connection used to
	 *            publish the message.The Quality of Service (QoS) level is an
	 *            agreement between sender and receiver of a message regarding
	 *            the guarantees of delivering a message. There are 3 QoS levels
	 *            in MQTT:
	 * 
	 *            At most once (0) : The minimal level is zero and it guarantees
	 *            a best effort delivery , At least once (1) : When using QoS
	 *            level 1, it is guarantees that a message will be delivered at
	 *            least once to the receiver , Exactly once (2) : The highest
	 *            QoS is 2, it guarantees that each message is received only
	 *            once by the counterpart. It is the safest and also the slowest
	 *            quality of service level .
	 *            </p>
	 * @param retained
	 *            :Whether or not the publish message should be retained by the
	 *            messaging engine. Sending a message with the retained set to
	 *            false will clear the retained message from the server.
	 * @throws NoClientSetException
	 * @throws MqttException
	 * @author Sameer Gauba
	 */
	void publishMessage(String topicName, String message, Integer publishQos, Boolean retained)
			throws NoClientSetException, MqttException;

	/**
	 * Sets the callback listener to use for events that happen asynchronously.
	 * There are a number of events that listener will be notified about. These
	 * include
	 * 
	 * A new message has arrived and is ready to be processed The connection to
	 * the server has been lost Delivery of a message to the server has
	 * completed. Other events that track the progress of an individual
	 * operation such as connect and subscribe can be tracked using the
	 * MqttToken passed to the operation
	 * 
	 * @param callBack
	 *            : the class implementing {@link MqttCallback} interface to
	 *            define callback for events related to the client
	 * @throws NoClientSetException
	 * @author Sameer Gauba
	 */
	void setMqttCallback(MqttCallback callBack) throws NoClientSetException;

	/**
	 * Subscribe to a topic, which may include wildcards
	 * 
	 * @param topicName
	 *            : the topic to subscribe to, which can include wildcards.
	 *            <p>
	 * @param subscriptionQos
	 *            :The maximum quality of service at which to subscribe.
	 *            Messages published at a lower quality of service will be
	 *            received at the published QoS. Messages published at a higher
	 *            quality of service will be received using the QoS specified on
	 *            the subscribe.There are 3 QoS levels in MQTT:
	 * 
	 *            At most once (0) : The minimal level is zero and it guarantees
	 *            a best effort delivery , At least once (1) : When using QoS
	 *            level 1, it is guarantees that a message will be delivered at
	 *            least once to the receiver , Exactly once (2) : The highest
	 *            QoS is 2, it guarantees that each message is received only
	 *            once by the counterpart. It is the safest and also the slowest
	 *            quality of service level .
	 *            </p>
	 * @throws NoClientSetException
	 * @throws MqttException
	 * @author Sameer Gauba
	 */
	void subscribeTopic(String topicName, Integer subscriptionQos) throws NoClientSetException, MqttException;

	/**
	 * Disconnects from the server.
	 * <p>
	 * An attempt is made to quiesce the client allowing outstanding work to
	 * complete before disconnecting. It will wait for a maximum of 30 seconds
	 * for work to quiesce before disconnecting. This method must not be called
	 * from inside {@link MqttCallback} methods.
	 * </p>
	 * 
	 * @throws MqttException
	 * @throws NoClientSetException
	 */
	void disconnectClient() throws MqttException, NoClientSetException;

	/**
	 * Disconnects from the server.
	 * <p>
	 * The client will wait for all {@link MqttCallback} methods to complete. It
	 * will then wait for up to the quiesce timeout to allow for work which has
	 * already been initiated to complete - for example, it will wait for the
	 * QoS 2 flows from earlier publications to complete. When work has
	 * completed or after the quiesce timeout, the client will disconnect from
	 * the server. If the cleanSession flag was set to false and is set to false
	 * the next time a connection is made QoS 1 and 2 messages that were not
	 * previously delivered will be delivered.
	 * </p>
	 *
	 * <p>
	 * This is a blocking method that returns once disconnect completes
	 * </p>
	 *
	 * @param quiesceTimeout
	 *            the amount of time in milliseconds to allow for existing work
	 *            to finish before disconnecting. A value of zero or less means
	 *            the client will not quiesce.
	 * @throws MqttException
	 *             : if a problem is encountered while disconnecting.
	 * @throws NoClientSetException
	 *             : if the client is not connected to the server.
	 */
	void disconnectClient(Long quiesceTimeout) throws NoClientSetException, MqttException;

	/**
	 * Informs the user whether or not client is connected to broker url.
	 * 
	 * @return returns true if the client is connected to the broker url else
	 *         return false
	 * @author Sameer Gauba
	 * @throws NoClientSetException
	 */
	Boolean isClientConnected() throws NoClientSetException;

}