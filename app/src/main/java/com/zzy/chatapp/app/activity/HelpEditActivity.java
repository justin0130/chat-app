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
import com.zzy.chatapp.app.tools.OnLoadDialog;
import com.zzy.chatapp.app.tools.RequestServerUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by justin on 3/8/16.
 */
public class HelpEditActivity extends Activity implements View.OnClickListener {
	Button btnSubmit;
	TextView tvTitleName;
	EditText etHelpDetails;
	EditText etelpMoney;
	EditText etHelpAddress;
	EditText etHelpName;
	EditText etHelpPhone;

	OnLoadDialog onLoadDialog;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.obj == null) {
				onLoadDialog.cancel();
				Toast.makeText(HelpEditActivity.this, R.string.submit_failure, Toast.LENGTH_SHORT).show();
				return;
			}
			String obj = msg.obj.toString();
			try {
				JSONObject json = new JSONObject(obj);
				String status = json.getString("status");
				if("0".equals(status)) {
					onLoadDialog.cancel();
					Toast.makeText(HelpEditActivity.this, R.string.submit_failure, Toast.LENGTH_SHORT).show();
					return;
				}
				onLoadDialog.cancel();
				Toast.makeText(HelpEditActivity.this, R.string.submit_success, Toast.LENGTH_SHORT).show();
				finish();
			} catch (JSONException e) {
				onLoadDialog.cancel();
				e.printStackTrace();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_edit);

		initViews();

		btnSubmit.setVisibility(View.VISIBLE);
		btnSubmit.setOnClickListener(this);
		tvTitleName.setText(R.string.edit);
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
			case R.id.btn_help_submit:
				onLoadDialog = new OnLoadDialog(HelpEditActivity.this);
				onLoadDialog.show();
				submitRequestHelp();
				break;
			default:
				break;
		}
	}

	void initViews() {
		btnSubmit = (Button) findViewById(R.id.btn_help_submit);
		tvTitleName = (TextView) findViewById(R.id.tv_title_name);
		etHelpDetails = (EditText) findViewById(R.id.et_help_details);
		etelpMoney = (EditText) findViewById(R.id.et_help_money);
		etHelpAddress = (EditText) findViewById(R.id.et_help_address);
		etHelpName = (EditText) findViewById(R.id.et_help_name);
		etHelpPhone = (EditText) findViewById(R.id.et_help_phone);
	}

	private void submitRequestHelp() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("content", etHelpDetails.getText().toString());
		map.put("reward", etelpMoney.getText().toString());
		map.put("address", etHelpAddress.getText().toString());
		map.put("nickName", etHelpName.getText().toString());
		map.put("phone", etHelpPhone.getText().toString());
		RequestServerUtils.submitRequestHelp(handler, map);
	}
}
