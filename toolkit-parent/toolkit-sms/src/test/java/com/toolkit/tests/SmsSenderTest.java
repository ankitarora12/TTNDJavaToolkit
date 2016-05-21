package com.toolkit.tests;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.junit.Test;
import org.mockito.asm.ClassAdapter;

import static org.junit.Assert.*;

import com.toolkit.sms.SmsSender;
import com.toolkit.sms.exceptions.UrlNotSetException;
import com.toolkit.sms.impl.SmsSenderImpl;
import com.toolkit.sms.utils.SmsSenderUtil;

public class SmsSenderTest {

	@Test
	public void successTest() throws IOException, UrlNotSetException {
		SmsSender sender = SmsSenderUtil.getSmsSender("http://www.smszone.in/sendsms.asp");
		sender.getApiUrl();
		Map<String, String> requestParams = sender.getRequestParams();

		requestParams.put("page", "SendSmsBulk");
		// requestParams.put("username", "918285503949");
		requestParams.put("password", "0D16");
		requestParams.put("number", "8285503949");
		requestParams.put("message", "Some message!");

		assertEquals("ERROR INVALID USER", sender.sendMsg());
	}

	@Test(expected = UrlNotSetException.class)
	public void exceptionTest() throws IOException, UrlNotSetException {
		SmsSender sender = new SmsSenderImpl();
		sender.sendMsg();
	}

	@Test
	public void failureTest() throws IOException, UrlNotSetException {
		SmsSender sender = SmsSenderUtil.getSmsSender();
		sender.setApiUrl("http://www.smszone.in/sendsms.asp");
		assertNotEquals("SUCCESS", sender.sendMsg());
	}

	@Test
	public void exceptionCreationTest() throws IOException, UrlNotSetException, ClassNotFoundException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		UrlNotSetException exception = new UrlNotSetException("urlException");
		exception.toString();
	}

}
