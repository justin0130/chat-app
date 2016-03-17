package com.zzy.chatapp.app.tools;

import android.os.Handler;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by justin on 3/16/16.
 */
public class RequestServerUtils {
	public static final String BASE_URL = "http://192.168.1.110:8080/grailsExercise1/home/";
	public static final String CHECK_LOGIN = "checkLogin";
	public static final String TEST = "test";

	public static void checkLogin(Handler handler, String username, String password) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", password);
		connToServer(handler, BASE_URL + CHECK_LOGIN, map);
	}

	public static void getServerData(Handler handler) {
		connToServer(handler, BASE_URL + TEST, null);
	}

	private static void connToServer(final Handler handler, final String url, final Map<String, String> map) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String response = "";
					if(map == null) {
						response = HttpUtils.getRequest(url);
					} else {
						response = HttpUtils.postRequest(url, map);
					}
					Message msg = new Message();
					msg.obj = response;
					msg.setTarget(handler);
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
