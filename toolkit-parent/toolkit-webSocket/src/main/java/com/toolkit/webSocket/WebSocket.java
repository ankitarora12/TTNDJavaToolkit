/**
 * 
 */
package com.toolkit.webSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

/**
 * @author nidhi
 *
 */
public abstract class WebSocket {

	private static final Map<String,List<Session>> connections = new HashMap<String, List<Session>>();
	
	@OnOpen
	public void sessionStart(Session session, String sessionKey) {
		addSession(session,sessionKey);
	}

	private void addSession(Session session, String sessionKey) {
		
		List<Session> sessions=connections.get(sessionKey);
		if(sessions!=null)
		{
			sessions.add(session);
			connections.put(sessionKey, sessions);
		}
		else{
			sessions=new ArrayList<Session>();
			sessions.add(session);
			connections.put(sessionKey,sessions);
		}
		
	}
	
	@OnError
	public void errorOccur(Throwable t) throws Throwable {
		t.printStackTrace();
	}

	@OnClose
	public void endSession(Session session, String sessionKey) {
		removeConnection(session,sessionKey);
	}

	private static void removeConnection(Session session, String sessionKey) {
		if(session!=null)
		{
			List<Session> sessions=connections.get(sessionKey);
			sessions.remove(session);
			connections.put(sessionKey, sessions);
		}
		else{
			connections.remove(sessionKey);
		}

	}
	
	@OnMessage
	public static void sendMessage(String sessionKey,String dataJson) {
		if (connections != null && connections.size() > 0) {
			Iterator<Session> iterator = connections.get(sessionKey).iterator();
			
			while (iterator.hasNext()) {
				Session session = iterator.next();
				try {
					synchronized (session) {
						session.getBasicRemote().sendText(dataJson);
					}
				} catch (IOException e) {
					try {
						session.close();
					} catch (IOException e1) {
					}
				}
			}

		}
	}
	
}
