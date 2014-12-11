package de.tavendo.autobahn;

import java.util.Random;

import android.util.Log;
import android.webkit.WebView;

public class WebSocketFactory {

	/**
	 * android Websocketfactory(将与手动载入的js方法“WebSocketFactory”进行绑定)
	 * Hcx
	 */
	
	private WebView appView;
	private WebSocketConnectionHandler h=null;
	private WebSocketConnection wsc;
	
	//构造方法
	public WebSocketFactory(WebView v){
		appView=v;
	}
	
	//websocket对象实例化
	public WebSocketConnectionHandler getInstance(String url){
		
		try {
			wsc = new WebSocketConnection();//定义connection对象
			h=new WebSocketConnectionHandler(appView,getRandonUniqueId()){
				/**
				 * 接口实现
				 */
				@Override
				public void send(String message) {
					// TODO Auto-generated method stub
					super.send(message);
					wsc.sendTextMessage(message);
				}
				
			};
			wsc.connect(url, h);//建立连接
			return h;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("connect websocket error!", e+"");
			return null;
		}
		
	}
	
	private String getRandonUniqueId() {
		return "WEBSOCKET." + new Random().nextInt(100);
	}

	public void CloseConnect(){
		wsc.onClose(0, "");
	}
	
	
	
	
}
