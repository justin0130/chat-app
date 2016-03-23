package com.zzy.chatapp.app.fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.zzy.chatapp.app.R;
import com.zzy.chatapp.app.tools.RequestServerUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by justin on 3/2/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MeFragment extends Fragment {
	TextView tvTitleName;
	TextView tvMeName;
	TextView tvMeAge;
	TextView tvMeSign;
	TextView tvMeHelpcount;
	TextView tvMeAddress;
	TextView tvMePhone;

	Handler handler;
	ProgressDialog pDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if(msg.obj == null) {
					pDialog.cancel();
					return;
				}
				String json = msg.obj.toString();
				try {
					JSONObject object = new JSONObject(json);
					Map<String, String> map = new HashMap<String, String>();
					map.put("userName", object.getString("userName"));
					map.put("age", object.getString("age"));
					map.put("sign", object.getString("sign"));
					map.put("count", object.getString("count"));
					map.put("address", object.getString("address"));
					map.put("phone", object.getString("phone"));
					setViews(map);
					pDialog.cancel();
				} catch (JSONException e) {
					pDialog.cancel();
					e.printStackTrace();
				}
			}
		};

		getUserDetails();

		View view = inflater.inflate(R.layout.fragment_me, null);

		initViews(view);
		tvTitleName.setText(R.string.me);

		return view;
	}

	private void getUserDetails() {
		pDialog = new ProgressDialog(getActivity());
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.setMessage("正在加载中...");
		pDialog.show();

		RequestServerUtils.getUserDetails(handler);
	}

	private void setViews(Map<String, String> map) {
		tvMeName.setText(map.get("userName"));
		tvMeAge.setText(map.get("age"));
		tvMeSign.setText(map.get("sign"));
		tvMeHelpcount.setText(map.get("count"));
		tvMeAddress.setText(map.get("address"));
		tvMePhone.setText(map.get("phone"));
	}

	void initViews(View view) {
		tvTitleName = (TextView) view.findViewById(R.id.tv_title_name);
		tvMeName = (TextView) view.findViewById(R.id.tv_me_name);
		tvMeAge = (TextView) view.findViewById(R.id.tv_me_age);
		tvMeSign = (TextView) view.findViewById(R.id.tv_me_sign);
		tvMeHelpcount = (TextView) view.findViewById(R.id.tv_me_helpcount);
		tvMeAddress = (TextView) view.findViewById(R.id.tv_me_address);
		tvMePhone = (TextView) view.findViewById(R.id.tv_me_phone);
	}
}
