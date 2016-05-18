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
	public static String USER_ID = "0";

//	public static String BASE_URL_SERVER = "http://192.168.56.1:8080/grailsExercise1/home/";
//	public static String LOGIN = "login";
//	public static String REGISTER = "register";
//	public static String UPDATE_USER_Details = "updateUserDetails";
//	public static String GET_USER_DETAILS = "getUserDetails";
//	public static String GET_HELP_LIST = "getHelpList";
//	public static String SUBMIT_REQUEST_POST = "submitRequestPost";
//	public static String GET_POST_DETAILS = "getPostDetails";
//	public static String TOOK_POST = "tookPost";

	public static String BASE_URL_SERVER = "http://ip:8080/JSON/";
	public static String LOGIN = "Login.aspx";
	public static String REGISTER = "Register.aspx";
	public static String UPDATE_USER_Details = "updateUserMess.aspx";
	public static String GET_USER_DETAILS = "getUserDetails.aspx";
	public static String GET_HELP_LIST = "getHelpReceive.aspx";
	public static String SUBMIT_REQUEST_POST = "Post.aspx";
	public static String GET_POST_DETAILS = "getPostDetails.aspx";
	public static String TOOK_POST = "receivePost.aspx";

	public static void setBaseUrl(String ip, String port) {
		IP = ip;
		PORT = port;
		BASE_URL_SERVER = BASE_URL_SERVER.replaceFirst("//[^/:]+:[0-9]*", ("//" + IP + ":" + PORT));
	}

	public static void setUserId(String userId) {
		USER_ID = userId;
	}

	public static void login(Handler handler, String username, String password) {
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

	public static void updateUserMess(Handler handler, Map<String, String> map) {
		map.put("userId", USER_ID);
		connToServer(handler, BASE_URL_SERVER + UPDATE_USER_Details, map);
	}

	public static void getHelpList(Handler handler) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", USER_ID);
		connToServer(handler, BASE_URL_SERVER + GET_HELP_LIST, map);
	}

	public static void submitRequestHelp(Handler handler, Map<String, String> map) {
		map.put("userId", USER_ID);
		connToServer(handler, BASE_URL_SERVER + SUBMIT_REQUEST_POST, map);
	}

	public static void getPostDetails(Handler handler, String postId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("postId", postId);
		connToServer(handler, BASE_URL_SERVER + GET_POST_DETAILS, map);
	}

	public static void tookPost(Handler handler, String postId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", USER_ID);
		map.put("postId", postId);
		connToServer(handler, BASE_URL_SERVER + TOOK_POST, map);
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
