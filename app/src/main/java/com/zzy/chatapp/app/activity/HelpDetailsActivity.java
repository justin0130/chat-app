package com.zzy.chatapp.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.zzy.chatapp.app.R;
import com.zzy.chatapp.app.fragment.HelpFragment;
import com.zzy.chatapp.app.tools.HttpUtils;
import com.zzy.chatapp.app.tools.OnLoadDialog;
import com.zzy.chatapp.app.tools.RequestServerUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by justin on 3/8/16.
 */
public class HelpDetailsActivity extends Activity implements View.OnClickListener {
	Button btnHelpSubmit;
	EditText etHelpDetails;
	EditText etHelpMoney;
	EditText etHelpAddress;
	EditText etHelpName;
	EditText etHelpPhone;
	TextView tvTitltName;

	private String postId;

	OnLoadDialog onLoadDialog;
	Handler contentHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg == null) {
				onLoadDialog.cancel();
				Toast.makeText(HelpDetailsActivity.this, R.string.get_post_details_failure, Toast.LENGTH_SHORT).show();
				return;
			}
			String obj = msg.obj.toString();
			if(HttpUtils.TIME_OUT.equals(obj)) {
				onLoadDialog.cancel();
				Toast.makeText(HelpDetailsActivity.this, R.string.connect_time_out, Toast.LENGTH_SHORT).show();
				return;
			}
			try {
				JSONObject json = new JSONObject(obj);
				String status = json.getString("status");
				if("0".equals(status)) {
					onLoadDialog.cancel();
					Toast.makeText(HelpDetailsActivity.this, R.string.get_post_details_failure, Toast.LENGTH_SHORT).show();
					return;
				}
				JSONObject content = json.getJSONObject("myPostDetails");
				setPostDetails(content);
				onLoadDialog.cancel();
			} catch (JSONException e) {
				onLoadDialog.cancel();
				e.printStackTrace();
			}
		}
	};

	Handler tookPostHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg == null) {
				onLoadDialog.cancel();
				Toast.makeText(HelpDetailsActivity.this, R.string.took_post_failure, Toast.LENGTH_SHORT).show();
				return;
			}
			String obj = msg.obj.toString();
			if(HttpUtils.TIME_OUT.equals(obj)) {
				onLoadDialog.cancel();
				Toast.makeText(HelpDetailsActivity.this,
						R.string.connect_time_out, Toast.LENGTH_SHORT).show();
				return;
			}
			try {
				JSONObject json = new JSONObject(obj);
				String status = json.getString("status");
				if("0".equals(status)) {
					onLoadDialog.cancel();
					Toast.makeText(HelpDetailsActivity.this,
							R.string.took_post_failure, Toast.LENGTH_SHORT).show();
					return;
				}
				onLoadDialog.cancel();
				Toast.makeText(HelpDetailsActivity.this,
						R.string.took_post_success, Toast.LENGTH_SHORT).show();

				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("postId", json.getString("postId"));
				map.put("nickName", json.getString("nickName"));
				map.put("content", json.getString("content"));
				HelpFragment.refresh(map, "help");

				finish();
			} catch (JSONException e) {
				e.printStackTrace();
				onLoadDialog.cancel();
				Toast.makeText(HelpDetailsActivity.this,
						R.string.error, Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
				finish();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_edit);

		Bundle bundle = getIntent().getExtras();
		postId = bundle.getString("postId");

		initViews();
		setViews();
		getContent();

		if("news".equals(bundle.getString("flag"))) {
			btnHelpSubmit.setVisibility(View.VISIBLE);
			btnHelpSubmit.setText(R.string.accept);
			btnHelpSubmit.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_help_submit:
				tookPost();
				break;
			default:
				break;
		}
	}

	void initViews() {
		btnHelpSubmit = (Button) findViewById(R.id.btn_help_submit);
		etHelpDetails = (EditText) findViewById(R.id.et_help_details);
		etHelpMoney = (EditText) findViewById(R.id.et_help_money);
		etHelpAddress = (EditText) findViewById(R.id.et_help_address);
		etHelpName = (EditText) findViewById(R.id.et_help_name);
		etHelpPhone = (EditText) findViewById(R.id.et_help_phone);
		tvTitltName = (TextView) findViewById(R.id.tv_title_name);
	}

	void setViews() {
		etHelpDetails.setText("");
		etHelpDetails.setFocusable(false);
		etHelpDetails.setFocusableInTouchMode(false);

		etHelpMoney.setText("");
		etHelpMoney.setFocusable(false);
		etHelpMoney.setFocusableInTouchMode(false);

		etHelpAddress.setText("");
		etHelpAddress.setFocusable(false);
		etHelpAddress.setFocusableInTouchMode(false);

		etHelpName.setText("");
		etHelpName.setFocusable(false);
		etHelpName.setFocusableInTouchMode(false);

		etHelpPhone.setText("");
		etHelpPhone.setFocusable(false);
		etHelpPhone.setFocusableInTouchMode(false);

		tvTitltName.setText(R.string.details);
	}

	private void getContent() {
		onLoadDialog = new OnLoadDialog(HelpDetailsActivity.this);
		onLoadDialog.show();
		RequestServerUtils.getPostDetails(contentHandler, postId);
	}

	private void setPostDetails(JSONObject content) throws JSONException {
		etHelpDetails.setText(content.getString("content"));
		etHelpMoney.setText(content.getString("reward"));
		etHelpAddress.setText(content.getString("address"));
		etHelpName.setText(content.getString("nickName"));
		etHelpPhone.setText(content.getString("phone"));
	}

	private void tookPost() {
		onLoadDialog = new OnLoadDialog(HelpDetailsActivity.this);
		onLoadDialog.show();
		RequestServerUtils.tookPost(tookPostHandler, postId);
	}
}
