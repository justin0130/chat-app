package com.zzy.chatapp.app.tools;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by justin on 3/16/16.
 */
public class HttpUtils {
	public static HttpClient httpClient = new DefaultHttpClient();
	public static int CLIENT_TIMEOUT = 8000;
	public static String TIME_OUT = "-1";

	public static String getRequest(String url) throws Exception {
		HttpGet get = new HttpGet(url);
		HttpResponse httpResponse = httpClient.execute(get);
		if(httpResponse.getStatusLine().getStatusCode() == 200) {
			return EntityUtils.toString(httpResponse.getEntity());
		} else {
			Log.d("server code", (new Integer(httpResponse.getStatusLine()
				.getStatusCode())).toString());
			return null;
		}
	}

	public static String postRequest(String url, 
		Map<String, String> rawParams) throws Exception {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for(String key: rawParams.keySet()) {
			params.add(new BasicNameValuePair(key, rawParams.get(key)));
		}
		post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		//set timeout
		httpClient.getParams().setParameter(
			CoreConnectionPNames.CONNECTION_TIMEOUT, CLIENT_TIMEOUT);
		HttpParams httpParams = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, CLIENT_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, CLIENT_TIMEOUT);

		try {
			HttpResponse httpResponse = httpClient.execute(post);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				return result;
			}
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			return TIME_OUT;
		}
		return null;
	}
}
