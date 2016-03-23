package com.zzy.chatapp.app.tools;

import android.os.Handler;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by justin on 3/16/16.
 */
public class RequestServerUtils {
	public static String IP = "";
	public static String PORT = "";
	public static String USER_ID = "4";

	public static String BASE_URL_LOCAL = "http://" + IP + ":" + PORT + "/grailsExercise1/home/";
	public static String CHECK_LOGIN = "checkLogin";
//	public static String REGISTER = "register";
	public static String TEST = "test";

	public static String BASE_URL_SERVER = "http://" + IP + ":" + PORT + "/JSON/";
	public static String LOGIN = "Login.aspx";
	public static String REGISTER = "Register.aspx";
	public static String GET_USER_DETAILS = "getUserDetails.aspx";

	public static void setBaseUrl(String ip, String port) {
		IP = ip;
		PORT = port;
		BASE_URL_LOCAL = "http://" + ip + ":" + port + "/grailsExercise1/home/";
		BASE_URL_SERVER = "http://" + ip + ":" + port + "/JSON/";
	}

	public static void setUserId(String userId) {
		USER_ID = userId;
	}

	public static void checkLogin(Handler handler, String username, String password) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", username);
		map.put("password", password);
		connToServer(handler, BASE_URL_SERVER + LOGIN, map);
	}

	public static void register(Handler handler, String username, String password) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", username);
		map.put("password", password);
		connToServer(handler, BASE_URL_SERVER + REGISTER, map);
	}

	public static void getUserDetails(Handler handler) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", USER_ID);
		connToServer(handler, BASE_URL_SERVER + GET_USER_DETAILS, map);
	}

	public static void getServerData(Handler handler) {
		connToServer(handler, BASE_URL_LOCAL + TEST, null);
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
