package com.zzy.chatapp.app.fragment;

import android.annotation.TargetApi;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.zzy.chatapp.app.R;
import com.zzy.chatapp.app.tools.OnLoadDialog;
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

	OnLoadDialog onLoadDialog;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.obj == null) {
				onLoadDialog.cancel();
				Toast.makeText(getActivity(), R.string.no_user_information, Toast.LENGTH_SHORT).show();
				return;
			}
			String json = msg.obj.toString();
			try {
				JSONObject object = new JSONObject(json);
				String status = object.getString("status");
				if("0".equals(status)) {
					onLoadDialog.cancel();
					Toast.makeText(getActivity(), R.string.no_user_information, Toast.LENGTH_SHORT).show();
					return;
				}
				JSONObject data = object.getJSONObject("UserMess");
				Map<String, String> map = new HashMap<String, String>();
				map.put("userName", data.getString("userName"));
				map.put("age", data.getString("age"));
				map.put("sign", data.getString("sign"));
				map.put("count", data.getString("count"));
				map.put("address", data.getString("address"));
				map.put("phone", data.getString("phone"));
				setViews(map);
				onLoadDialog.cancel();
			} catch (JSONException e) {
				onLoadDialog.cancel();
				e.printStackTrace();
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_me, null);
		initViews(view);
		tvTitleName.setText(R.string.me);

		return view;
	}

	void initViews(View view) {
		tvTitleName = (TextView) view.findViewById(R.id.tv_title_name);
		tvMeName = (TextView) view.findViewById(R.id.tv_me_name);
		tvMeAge = (TextView) view.findViewById(R.id.tv_me_age);
		tvMeSign = (TextView) view.findViewById(R.id.tv_me_sign);
		tvMeHelpcount = (TextView) view.findViewById(R.id.tv_me_helpcount);
		tvMeAddress = (TextView) view.findViewById(R.id.tv_me_address);
		tvMePhone = (TextView) view.findViewById(R.id.tv_me_phone);

		getUserDetails();
	}

	private void setViews(Map<String, String> map) {
		tvMeName.setText(map.get("userName"));
		tvMeAge.setText(map.get("age"));
		tvMeSign.setText(map.get("sign"));
		tvMeHelpcount.setText(map.get("count"));
		tvMeAddress.setText(map.get("address"));
		tvMePhone.setText(map.get("phone"));
	}

	private void getUserDetails() {
		onLoadDialog = new OnLoadDialog(getActivity());
		onLoadDialog.show();

		RequestServerUtils.getUserDetails(handler);
	}
}
