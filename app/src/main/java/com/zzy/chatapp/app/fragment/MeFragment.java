package com.zzy.chatapp.app.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.zzy.chatapp.app.R;
import com.zzy.chatapp.app.activity.ToolsActivity;
import com.zzy.chatapp.app.tools.HttpUtils;
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
public class MeFragment extends Fragment implements View.OnClickListener {
	boolean viewIsEditable = false;

	TextView tvTitleName;
	EditText etMeName;
	EditText etMeAge;
	EditText etMeSign;
	EditText etMeHelpcount;
	EditText etMeAddress;
	EditText etMePhone;
	ImageView ivTitleTools;
	ImageView ivTitleAdd;
	Button btnMeFinish;

	OnLoadDialog onLoadDialog;
	Handler getUserDetailsHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.obj == null) {
				onLoadDialog.cancel();
				Toast.makeText(getActivity(), R.string.no_user_information, 
					Toast.LENGTH_SHORT).show();
				return;
			}
			String json = msg.obj.toString();
			if(HttpUtils.TIME_OUT.equals(json)) {
				onLoadDialog.cancel();
				Toast.makeText(getActivity(), R.string.connect_time_out, 
					Toast.LENGTH_SHORT).show();
				return;
			}
			try {
				JSONObject object = new JSONObject(json);
				String status = object.getString("status");
				if("0".equals(status)) {
					onLoadDialog.cancel();
					Toast.makeText(getActivity(), R.string.no_user_information, 
						Toast.LENGTH_SHORT).show();
					return;
				}
				JSONObject data = object.getJSONObject("UserMess");
				Map<String, String> map = new HashMap<String, String>();
				map.put("userName", data.getString("nickName"));
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

	Handler updateUserDetails = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			setViewEnable(false);
			btnMeFinish.setVisibility(View.GONE);
			if(msg.obj == null) {
				onLoadDialog.cancel();
				Toast.makeText(getActivity(), R.string.chang_user_details_failure, Toast.LENGTH_SHORT).show();
				return;
			}
			String json = msg.obj.toString();
			if(HttpUtils.TIME_OUT.equals(json)) {
				onLoadDialog.cancel();
				Toast.makeText(getActivity(), R.string.connect_time_out, Toast.LENGTH_SHORT).show();
				return;
			}
			try {
				JSONObject object = new JSONObject(json);
				String status = object.getString("status");
				if("0".equals(status)) {
					onLoadDialog.cancel();
					Toast.makeText(getActivity(), R.string.chang_user_details_failure, Toast.LENGTH_SHORT).show();
					return;
				}
				Toast.makeText(getActivity(),
						R.string.chang_user_details_success, Toast.LENGTH_SHORT).show();
				viewIsEditable = false;
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
		getUserDetails();

		tvTitleName.setText(R.string.me);
		ivTitleTools.setVisibility(View.VISIBLE);
		ivTitleTools.setImageResource(R.mipmap.tools);
		ivTitleAdd.setVisibility(View.VISIBLE);
		ivTitleAdd.setImageResource(R.mipmap.edit);

		ivTitleTools.setOnClickListener(this);
		ivTitleAdd.setOnClickListener(this);
		btnMeFinish.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.iv_title_add:
				viewIsEditable = !viewIsEditable;
				setViewEnable(viewIsEditable);
				int visible = viewIsEditable ? View.VISIBLE : View.GONE;
				btnMeFinish.setVisibility(visible);
				break;
			case R.id.btn_me_finish:
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("nickName", etMeName.getText().toString());
				map.put("age", etMeAge.getText().toString());
				map.put("sign", etMeSign.getText().toString());
				map.put("count", etMeHelpcount.getText().toString());
				map.put("address", etMeAddress.getText().toString());
				map.put("phone", etMePhone.getText().toString());

				onLoadDialog.show();
				RequestServerUtils.updateUserMess(updateUserDetails, map);
				break;
			case R.id.iv_title_back:
				startActivity(new Intent(getActivity(), ToolsActivity.class));
				break;
			default:
				break;
		}
	}

	void initViews(View view) {
		tvTitleName = (TextView) view.findViewById(R.id.tv_title_name);
		etMeName = (EditText) view.findViewById(R.id.et_me_name);
		etMeAge = (EditText) view.findViewById(R.id.et_me_age);
		etMeSign = (EditText) view.findViewById(R.id.et_me_sign);
		etMeHelpcount = (EditText) view.findViewById(R.id.et_me_helpcount);
		etMeAddress = (EditText) view.findViewById(R.id.et_me_address);
		etMePhone = (EditText) view.findViewById(R.id.et_me_phone);
		ivTitleTools = (ImageView) view.findViewById(R.id.iv_title_back);
		ivTitleAdd = (ImageView) view.findViewById(R.id.iv_title_add);
		btnMeFinish = (Button) view.findViewById(R.id.btn_me_finish);
	}

	private void setViews(Map<String, String> map) {
		etMeName.setText(map.get("userName"));
		etMeAge.setText(map.get("age"));
		etMeSign.setText(map.get("sign"));
		etMeHelpcount.setText(map.get("count"));
		etMeAddress.setText(map.get("address"));
		etMePhone.setText(map.get("phone"));
	}

	private void getUserDetails() {
		onLoadDialog = new OnLoadDialog(getActivity());
		onLoadDialog.show();

		RequestServerUtils.getUserDetails(getUserDetailsHandler);
	}

	private void setViewEnable(boolean isEnable) {
		etMeName.setEnabled(isEnable);
		etMeAge.setEnabled(isEnable);
		etMeSign.setEnabled(isEnable);
		etMeHelpcount.setEnabled(isEnable);
		etMeAddress.setEnabled(isEnable);
		etMePhone.setEnabled(isEnable);
	}
}
