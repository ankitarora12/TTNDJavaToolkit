package com.toolkit.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.toolkit.mqtt.MqttMessageClient;
import com.toolkit.mqtt.callable.ConnectCallable;
import com.toolkit.mqtt.exceptions.BrokerUrlNotSetException;
import com.toolkit.mqtt.exceptions.NoClientSetException;
import com.toolkit.mqtt.impl.MqttMessageClientImpl;

public class MqttMessageClientTest {

	Logger logger = LoggerFactory.getLogger(MqttMessageClientTest.class);

	private static String directoryPath;

	@BeforeClass
	public static void init() {
		directoryPath = "";
		directoryPath += "mqtt_lock_files" + File.separator + "lockFiles";

		File f = new File(directoryPath);
		if (!f.exists())
			f.mkdirs();

	}

	@Test
	public void connectionTest()
			throws MqttException, InterruptedException, BrokerUrlNotSetException, NoClientSetException {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer",
				directoryPath);
		client.connectClient(true, 15);
		assertEquals(true, client.isClientConnected());
		client.disconnectClient();
	}

	@Test
	public void connectionTest2()
			throws MqttException, InterruptedException, BrokerUrlNotSetException, NoClientSetException {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer");
		client.setDirectoryPath(null);
		client.connectClient(true, 15);
		assertEquals(true, client.isClientConnected());
		client.disconnectClient();
	}

	@Test(expected = BrokerUrlNotSetException.class)
	public void brokerExceptionTest()
			throws MqttException, InterruptedException, BrokerUrlNotSetException, NoClientSetException {
		MqttMessageClient client = new MqttMessageClientImpl();
		client.connectClient(true, 15);
	}

	@Test
	public void nullParamConnectionTest()
			throws MqttException, InterruptedException, BrokerUrlNotSetException, NoClientSetException {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer",
				directoryPath);
		client.connectClient(null, null);
		assertEquals(true, client.isClientConnected());
		client.disconnectClient();
	}

	@Test(expected = NoClientSetException.class)
	public void clientSubscribeExceptionTest() throws NoClientSetException, MqttException {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer",
				directoryPath);
		client.subscribeTopic("anyTopic", 0);
	}

	@Test(expected = NoClientSetException.class)
	public void clientDisconnectExceptionTest() throws NoClientSetException, MqttException {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer",
				directoryPath);
		client.disconnectClient();
	}

	@Test(expected = NoClientSetException.class)
	public void clientDisconnectParamExceptionTest() throws NoClientSetException, MqttException {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer",
				directoryPath);
		client.disconnectClient(10l);
	}

	@Test(expected = NoClientSetException.class)
	public void isClientConnectedExceptionTest() throws NoClientSetException, MqttException {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer",
				directoryPath);
		client.isClientConnected();
	}

	@Test(expected = NoClientSetException.class)
	public void clientUnsubscribeExceptionTest() throws NoClientSetException, MqttException {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer",
				directoryPath);
		client.unSubscribeTopic("anyTopic");
	}

	@Test
	public void unsubscribeNullParamTest()
			throws MqttException, NoClientSetException, InterruptedException, BrokerUrlNotSetException {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer",
				directoryPath);
		client.connectClient(true, 15);
		client.unSubscribeTopic(null);
		client.disconnectClient();
	}

	@Test(expected = NoClientSetException.class)
	public void clientPublishExceptionTest() throws NoClientSetException, MqttException {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer",
				directoryPath);
		client.publishMessage("any", "any", 1, true);
	}

	@Test
	public void publishNullParamTest()
			throws MqttException, InterruptedException, BrokerUrlNotSetException, NoClientSetException {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer",
				directoryPath);
		client.connectClient(true, 15);
		client.publishMessage(null, null, null, null);
		client.disconnectClient();
	}

	@Test
	public void publishMultipleTest()
			throws MqttException, InterruptedException, BrokerUrlNotSetException, NoClientSetException {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer",
				directoryPath);
		client.connectClient(true, 15);
		for (Integer i = 0; i < 5; i++)
			client.publishMessage("anyTopic", "New message : " + i, 1, false);
		client.disconnectClient();
	}

	@Test(expected = NoClientSetException.class)
	public void clientSetCallbackExceptionTest() throws NoClientSetException, MqttException {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer",
				directoryPath);
		client.setMqttCallback(null);
	}

	@Test
	public void subscribeMultipleTest()
			throws NoClientSetException, MqttException, InterruptedException, BrokerUrlNotSetException {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer",
				directoryPath);
		client.connectClient(true, 15);
		for (Integer i = 0; i < 5; i++)
			client.subscribeTopic("AnyTopic" + i, 0);
		client.disconnectClient();
	}

	@Test
	public void clientConnectedTest()
			throws MqttException, InterruptedException, BrokerUrlNotSetException, NoClientSetException {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer",
				directoryPath);
		client.connectClient(true, 15);
		assertTrue(client.isClientConnected());
		client.disconnectClient();
	}

	@Test
	public void clientNotConnectedTest()
			throws MqttException, InterruptedException, BrokerUrlNotSetException, NoClientSetException {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer",
				directoryPath);
		client.connectClient(true, 15);
		client.disconnectClient(10l);
		assertFalse(client.isClientConnected());
	}

	@Test
	public void getterSetterTest()
			throws MqttException, InterruptedException, BrokerUrlNotSetException, NoClientSetException {
		MqttMessageClient client = new MqttMessageClientImpl();
		client.setBrokerUrl("tcp://iot.eclipse.org:1883");
		client.setUsername("sameer");
		client.setPassword("sameer");
		client.connectClient(true, 15);
		client.getUsername();
		client.getPassword();
		client.getBrokerUrl();
		client.unSubscribeTopic("AnyTopic");
	}

	@Test
	public void unsubscribeTopicTest()
			throws MqttException, InterruptedException, BrokerUrlNotSetException, NoClientSetException {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer",
				directoryPath);
		client.connectClient(true, 15);
		client.unSubscribeTopic("someOtherTopic");
		client.disconnectClient();
		return;
	}

	@Test
	public void NoClientSetExceptionTest() {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer",
				directoryPath);
		NoClientSetException exception = new NoClientSetException("clientNotSet");
		NoClientSetException exception2 = new NoClientSetException(new Exception());
		NoClientSetException exception3 = new NoClientSetException("clientNotSet", new Exception());
		NoClientSetException exception4 = new NoClientSetException("clientNotSet", new Exception(), true, true);
		try {
			client.setMqttCallback(null);
		} catch (NoClientSetException e) {
			e.toString();
		}
	}

	@Test
	public void BrokerUrlNotSetExceptionTest() throws MqttException, InterruptedException {
		MqttMessageClient client = new MqttMessageClientImpl();
		BrokerUrlNotSetException exception = new BrokerUrlNotSetException("urlNotSet");
		BrokerUrlNotSetException exception2 = new BrokerUrlNotSetException(new Exception());
		BrokerUrlNotSetException exception3 = new BrokerUrlNotSetException("urlNotSet", new Exception());
		BrokerUrlNotSetException exception4 = new BrokerUrlNotSetException("urlNotSet", new Exception(), true, true);
		try {
			client.connectClient(true, 15);
		} catch (BrokerUrlNotSetException e) {
			e.toString();
		}
	}

	@Test
	public void callbackTest()
			throws MqttException, InterruptedException, BrokerUrlNotSetException, NoClientSetException {
		MqttMessageClient client = new MqttMessageClientImpl("tcp://iot.eclipse.org:1883", "sameer", "sameer",
				directoryPath);
		client.connectClient(true, 0);
		MqttCallback callback = Mockito.mock(MqttCallback.class);
		client.setMqttCallback(callback);
		client.subscribeTopic("AnyTopic", 0);
		client.publishMessage("AnyTopic", "TheMessage", 0, false);
		client.setUsername(null);
		client.setPassword(null);
		client.connectClient(null, null);
		client.disconnectClient();
		return;
	}

	@Test
	public void reconnectTest() throws MqttException, InterruptedException {
		MqttClient client = new MqttClient("tcp://iot.eclipse.org:1883", "123");
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(new ConnectCallable(client, null));
		long t = System.currentTimeMillis();
		long end = t + 15000;
		while (System.currentTimeMillis() < end) {
		}
	}

	@AfterClass
	public static void destroy() {
		directoryPath = "";
		directoryPath += "mqtt_lock_files" + File.separator;
		File f = new File(directoryPath);
		if (f.exists()) {
			try {
				FileUtils.deleteDirectory(f);
			} catch (IOException e) {
			}
		}
	}
}
