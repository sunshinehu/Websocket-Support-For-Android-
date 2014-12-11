/******************************************************************************
 *
 *  Copyright 2011-2012 Tavendo GmbH
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package de.tavendo.autobahn;

import android.webkit.WebView;

/**
 * Hcx modify
 * 根据支持tomcat websocket协议源代码修改
 * 该类作用（将onopen,onclose,onmessage,getId,send 几个方法提供给js调用，对于返回的数据通过buildjs方法返回并将其作为js加载到页面中）
 * 
 */
/**
 * WebSockets event handler. Users will usually provide an instance of a class
 * derived from this to handle WebSockets received messages and open/close events
 */
public class WebSocketConnectionHandler implements WebSocket.ConnectionHandler {

	private String id;
	private WebView appView;
	
	/**
	 * An empty string
	 */
	private static String BLANK_MESSAGE = "";
	/**
	 * The javascript method name for onOpen event.
	 */
	private static String EVENT_ON_OPEN = "onopen";
	/**
	 * The javascript method name for onMessage event.
	 */
	private static String EVENT_ON_MESSAGE = "onmessage";
	/**
	 * The javascript method name for onClose event.
	 */
	private static String EVENT_ON_CLOSE = "onclose";
	/**
	 * The javascript method name for onError event.
	 */
	private static String EVENT_ON_ERROR = "onerror";
	
	/**
	 * 构造方法,初始化
	 */
	public WebSocketConnectionHandler(WebView v,String ids){
		appView=v;
		id=ids;
	}
	
	/**
	 * 方法与js绑定
	 */
	
   /**
    * Fired when the WebSockets connection has been established.
    * After this happened, messages may be sent.
    */
   public void onOpen() {
	   appView.loadUrl(buildJavaScriptData(EVENT_ON_OPEN, BLANK_MESSAGE));
   }

   /**
    * Fired when the WebSockets connection has deceased (or could
    * not established in the first place).
    *
    * @param code       Close code.
    * @param reason     Close reason (human-readable).
    */
   public void onClose(int code, String reason) {
	   appView.loadUrl(buildJavaScriptData(EVENT_ON_CLOSE, BLANK_MESSAGE));
   }

   /**
    * Fired when a text message has been received (and text
    * messages are not set to be received raw).
    *
    * @param payload    Text message payload or null (empty payload).
    */
   public void onMessage(String payload) {
	   appView.loadUrl(buildJavaScriptData(EVENT_ON_MESSAGE, payload));
   }

   /**
    * Fired when a text message has been received (and text
    * messages are set to be received raw).
    *
    * @param payload    Text message payload as raw UTF-8 or null (empty payload).
    */
   public void onRawTextMessage(byte[] payload) {
   }

   /**
    * Fired when a binary message has been received.
    *
    * @param payload    Binar message payload or null (empty payload).
    */
   public void onBinaryMessage(byte[] payload) {
   }
   
   public void onError(final Throwable t){
	   appView.loadUrl(buildJavaScriptData(EVENT_ON_ERROR, t.getMessage()));
   }
   
   public void send(String message){
	   
   }

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	
	/**
	 * js返回
	 * @param event
	 * @param msg
	 * @return
	 */
	private String buildJavaScriptData(String event, String msg) {
		String _d = "javascript:WebSocket." + event + "(" + "{" + "\"_target\":\"" + id + "\"," + "\"data\":'" + msg
				+ "'" + "}" + ")";
		return _d;
	}
	
}
