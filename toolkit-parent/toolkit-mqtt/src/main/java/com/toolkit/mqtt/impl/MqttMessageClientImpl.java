package com.toolkit.mqtt.impl;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.toolkit.mqtt.MqttMessageClient;
import com.toolkit.mqtt.callable.ConnectCallable;
import com.toolkit.mqtt.exceptions.BrokerUrlNotSetException;
import com.toolkit.mqtt.exceptions.NoClientSetException;

/**
 * Allows a user to connect to any broker of mqtt server. After connecting the
 * user can subscribe to topics on the server and create new topics. User may
 * also send message to any topic on the server. User may also set his own
 * MqttCallBack in order to receive messages from his subscribed topics. For
 * this the username, password and brokerUrl fields of this class must be set.
 * brokerUrl is a string representing the url of the broker providing the mqtt
 * service. username and password identify a particular user on the broker's
 * server. After setting all the fields clientConnect method can be used to
 * connect the client to the broker url.
 * 
 * @author Sameer Gauba
 *
 */
public class MqttMessageClientImpl implements MqttMessageClient {
	
	Logger logger = LoggerFactory.getLogger(MqttMessageClientImpl.class);

	private MqttClient myClient;
	private MqttConnectOptions connOpt;
	private MqttCallback userCallback;
	private Boolean cleanSession;
	private Integer keepAliveInterval;

	private String clientId;
	private String brokerUrl;
	private String username;
	private String password;
	private String directoryPath;

	/**
	 * Allows a user to connect to any broker of mqtt server. After connecting
	 * the user can subscribe to topics on the server and create new topics.
	 * User may also send message to any topic on the server. User may also set
	 * his own MqttCallBack in order to receive messages from his subscribed
	 * topics. For this the username, password and brokerUrl fields of this
	 * class must be set. brokerUrl is a string representing the url of the
	 * broker providing the mqtt service. username and password identify a
	 * particular user on the broker's server. After setting all the fields
	 * clientConnect method can be used to connect the client to the broker url.
	 * 
	 * @author Sameer Gauba
	 * @param brokerUrl
	 *            : brokerUrl is a string representing the url of the broker
	 *            providing the mqtt service.
	 * @param username
	 *            : This string represents the username to connect a client to
	 *            the brokerUrl
	 * @param password
	 *            : This string represents the password to connect a client to
	 *            the brokerUrl
	 * @param directoryPath
	 *            : If this parameter is set, file-based persistent data store
	 *            is created within the specified directory.
	 */
	public MqttMessageClientImpl(String brokerUrl, String username, String password, String directoryPath) {
		super();
		this.brokerUrl = brokerUrl;
		this.username = username;
		this.password = password;
		this.directoryPath = directoryPath;
		idGenerate(username);
	}

	/**
	 * Allows a user to connect to any broker of mqtt server. After connecting
	 * the user can subscribe to topics on the server and create new topics.
	 * User may also send message to any topic on the server. User may also set
	 * his own MqttCallBack in order to receive messages from his subscribed
	 * topics. For this the username, password and brokerUrl fields of this
	 * class must be set. brokerUrl is a string representing the url of the
	 * broker providing the mqtt service. username and password identify a
	 * particular user on the broker's server. After setting all the fields
	 * clientConnect method can be used to connect the client to the broker url.
	 * 
	 * @author Sameer Gauba
	 * @param brokerUrl
	 *            : brokerUrl is a string representing the url of the broker
	 *            providing the mqtt service.
	 * @param username
	 *            : This string represents the username to connect a client to
	 *            the brokerUrl
	 * @param password
	 *            : This string represents the password to connect a client to
	 *            the brokerUrl
	 */
	public MqttMessageClientImpl(String brokerUrl, String username, String password) {
		super();
		this.brokerUrl = brokerUrl;
		this.username = username;
		this.password = password;
		idGenerate(username);
	}

	/**
	 * Allows a user to connect to any broker of mqtt server. After connecting
	 * the user can subscribe to topics on the server and create new topics.
	 * User may also send message to any topic on the server. User may also set
	 * his own MqttCallBack in order to receive messages from his subscribed
	 * topics. For this the username, password and brokerUrl fields of this
	 * class must be set. brokerUrl is a string representing the url of the
	 * broker providing the mqtt service. username and password identify a
	 * particular user on the broker's server. After setting all the fields
	 * clientConnect method can be used to connect the client to the broker url.
	 * 
	 * @author Sameer Gauba
	 *
	 */
	public MqttMessageClientImpl() {
		super();
		idGenerate("");
	}

	@Override
	public String getDirectoryPath() {
		if (directoryPath == null)
			directoryPath = "mqtt_lock_files" + File.separator + "lockFiles";
		return directoryPath;
	}

	/**
	 * If this parameter is set, file-based persistent data store is created
	 * within the specified directory.
	 * 
	 * @author Sameer Gauba
	 * 
	 */
	@Override
	public void setDirectoryPath(String directoryPath) {
		this.directoryPath = directoryPath;
	}

	@Override
	public String getBrokerUrl() {
		return brokerUrl;
	}

	/**
	 * Accepts a string that represents the url of the broker providing mqtt
	 * service
	 * 
	 * @author Sameer Gauba
	 * @param brokerUrl
	 */
	@Override
	public void setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}

	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * @author Sameer Gauba This string represents the username to connect a
	 *         client to the brokerUrl
	 * @param username
	 */
	@Override
	public void setUsername(String username) {
		this.username = username;
		idGenerate(username);
	}

	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * This string represents the password to connect a client to the brokerUrl
	 * 
	 * @author Sameer Gauba
	 * @param password
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

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
	 *            information from a previous persistent session. If set to
	 *            null, default value is true.
	 *            </p>
	 * @param keepAliveInterval
	 *            : The keep alive is a time interval, the clients commits to by
	 *            sending regular PING Request messages to the broker. The
	 *            broker response with PING Response and this mechanism will
	 *            allow both sides to determine if the other one is still alive
	 *            and reachable. If set to null, default value is 60.
	 * @throws MqttException
	 * @throws InterruptedException
	 * @throws BrokerUrlNotSetException
	 */
	@Override
	public void connectClient(Boolean cleanSession, Integer keepAliveInterval)
			throws MqttException, InterruptedException, BrokerUrlNotSetException {

		if (brokerUrl == null)
			throw new BrokerUrlNotSetException();
		if (cleanSession == null)
			this.cleanSession = true;
		else
			this.cleanSession = cleanSession;
		if (keepAliveInterval == null)
			this.keepAliveInterval = 60;
		else
			this.keepAliveInterval = keepAliveInterval;

		connOpt = new MqttConnectOptions();

		connOpt.setCleanSession(this.cleanSession);
		connOpt.setKeepAliveInterval(this.keepAliveInterval);
		connOpt.setUserName(username);
		if (password != null)
			connOpt.setPassword(password.toCharArray());

		if (getDirectoryPath() != null)
			myClient = new MqttClient(brokerUrl, clientId, new MqttDefaultFilePersistence(directoryPath));

		try {
			myClient.connect(connOpt);
		} catch (MqttException me) {
			logger.info(me.toString(), me);
		}

		if (!myClient.isConnected()) {
			ExecutorService es = Executors.newSingleThreadExecutor();
			es.submit(new ConnectCallable(myClient, connOpt));
		}

		myClient.setCallback(getCallBack());
	}

	private MqttCallback getCallBack() {
		MqttCallback callbackLocal;
		callbackLocal = new MyCallback();
		return callbackLocal;
	}

	private String idGenerate(String username) {
		clientId = username + Math.random();
		return clientId;
	}

	/**
	 * Used to unsubscribe from a topic with the given topicName
	 * 
	 * @param topicName
	 *            : Name of the topic which the user wants to unsubscribe.
	 * @throws MqttException
	 * @throws NoClientSetException
	 * @author Sameer Gauba
	 */
	@Override
	public void unSubscribeTopic(String topicName) throws MqttException, NoClientSetException {
		if (myClient == null)
			throw new NoClientSetException("");
		if (topicName == null)
			return;
		myClient.unsubscribe(topicName);
	}

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
	 *            quality of service level. Default value is 0.
	 *            </p>
	 * @param retained
	 *            :Whether or not the publish message should be retained by the
	 *            messaging engine. Sending a message with the retained set to
	 *            false will clear the retained message from the server. Default
	 *            value is false.
	 * @throws NoClientSetException
	 * @throws MqttException
	 * @author Sameer Gauba
	 */
	@Override
	public void publishMessage(String topicName, String message, Integer publishQos, Boolean retained)
			throws NoClientSetException, MqttException {

		Integer qos;
		Boolean retain;
		if (myClient == null)
			throw new NoClientSetException();
		if (publishQos == null)
			qos = 0;
		else
			qos = publishQos;
		if (retained == null)
			retain = false;
		else
			retain = retained;
		if (topicName == null || message == null)
			return;

		MqttMessage mqttMessage = new MqttMessage(message.getBytes());
		mqttMessage.setQos(qos);
		mqttMessage.setRetained(retain);

		MqttTopic topic = myClient.getTopic(topicName);
		// Publish the message
		MqttDeliveryToken token = topic.publish(mqttMessage);
		// Wait until the message has been delivered to the broker
		token.waitForCompletion();
	}

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
	@Override
	public void setMqttCallback(MqttCallback callBack) throws NoClientSetException {
		if (myClient == null)
			throw new NoClientSetException();
		this.userCallback = callBack;

		myClient.setCallback(getCallBack());
	}

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
	@Override
	public void subscribeTopic(String topicName, Integer subscriptionQos) throws NoClientSetException, MqttException {
		if (myClient == null)
			throw new NoClientSetException();
		myClient.subscribe(topicName, subscriptionQos);
	}

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
	@Override
	public void disconnectClient() throws MqttException, NoClientSetException {
		if (myClient == null)
			throw new NoClientSetException();
		myClient.disconnect();
	}

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
	@Override
	public void disconnectClient(Long quiesceTimeout) throws NoClientSetException, MqttException {
		if (myClient == null)
			throw new NoClientSetException();
		myClient.disconnect(quiesceTimeout);
	}

	/**
	 * Informs the user whether or not client is connected to broker url.
	 * 
	 * @return returns true if the client is connected to the broker url else
	 *         return false
	 * @author Sameer Gauba
	 * @throws NoClientSetException
	 */
	@Override
	public Boolean isClientConnected() throws NoClientSetException {
		if (myClient == null)
			throw new NoClientSetException();
		return myClient.isConnected();
	}

	private class MyCallback implements MqttCallback {
		@Override
		public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
			if (userCallback != null)
				userCallback.messageArrived(arg0, arg1);
		}

		@Override
		public void deliveryComplete(IMqttDeliveryToken arg0) {
			if (userCallback != null)
				userCallback.deliveryComplete(arg0);
		}

		@Override
		public void connectionLost(Throwable arg0) {
			if (userCallback != null)
				userCallback.connectionLost(arg0);
			for (Integer count = 1; count <= 20; count++) {
				if (count % 3 == 0) {
					long t = System.currentTimeMillis();
					long end = t + 20000;
					while (System.currentTimeMillis() < end) {
						//counting 20 seconds...
					}
				}
				try {
					connectClient(cleanSession, keepAliveInterval);
				} catch (Exception e) {
					logger.info(e.toString(), e);
				}
				if (myClient.isConnected())
					break;
			}
		}
	}

}