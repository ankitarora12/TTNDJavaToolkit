package com.toolkit.mqtt.callable;

import java.util.concurrent.Callable;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.toolkit.mqtt.exceptions.NoClientSetException;

public class ConnectCallable implements Callable<Integer> {

	Logger logger = LoggerFactory.getLogger(ConnectCallable.class);
	private MqttClient myClient;
	private MqttConnectOptions connOpt;

	public ConnectCallable(MqttClient myClient, MqttConnectOptions connOpt) {
		super();
		this.myClient = myClient;
		this.connOpt = connOpt;
	}

	@Override
	public Integer call() throws MqttException, InterruptedException, NoClientSetException {

		Integer counter = 0;

		while (!myClient.isConnected() && counter < 20) {
			try {
				myClient.connect(connOpt);
			} catch (Exception e) {
				logger.info(e.toString(), e);
			}
			if (counter % 3 == 0)
				Thread.sleep(20000);

			counter++;
		}
		if (!myClient.isConnected())
			throw new NoClientSetException();
		return 1;
	}

}
