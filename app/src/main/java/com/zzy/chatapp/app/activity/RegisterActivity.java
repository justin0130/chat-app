package com.zzy.chatapp.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;
import com.zzy.chatapp.app.R;
import com.zzy.chatapp.app.tools.RequestServerUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by justin on 3/19/16.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
	Button btnRegister;
	EditText etRegisterName;
	EditText etRegisterPassword;
	ImageView ivTitleBack;
	TextView tvTitleName;
	ProgressDialog pDialog;

	Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		initViews();
		ivTitleBack.setVisibility(View.VISIBLE);
		tvTitleName.setText(R.string.register);

		btnRegister.setOnClickListener(this);
		ivTitleBack.setOnClickListener(this);

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if(msg.obj == null) {
					pDialog.cancel();
					Toast.makeText(RegisterActivity.this, R.string.register_failed, Toast.LENGTH_SHORT).show();
					return;
				}
				String json = msg.obj.toString();
				try {
					JSONObject object = new JSONObject(json);
					String status = object.getString("status");
					if("failed".equals(status)) {
						pDialog.cancel();
						Toast.makeText(RegisterActivity.this, R.string.register_failed, Toast.LENGTH_SHORT).show();
						return;
					}
					pDialog.cancel();
					Toast.makeText(RegisterActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();
					finish();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
	}

	private void initViews() {
		btnRegister = (Button) findViewById(R.id.btn_register);
		etRegisterName = (EditText) findViewById(R.id.et_register_name);
		etRegisterPassword = (EditText) findViewById(R.id.et_register_password);
		ivTitleBack = (ImageView) findViewById(R.id.iv_title_back);
		tvTitleName = (TextView) findViewById(R.id.tv_title_name);
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
			case R.id.btn_register:
				pDialog = new ProgressDialog(RegisterActivity.this);
				pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pDialog.setMessage("正在加载中...");
				pDialog.show();
				RequestServerUtils.register(handler, etRegisterName.getText().toString(), etRegisterPassword.getText().toString());
				break;
			case R.id.iv_title_back:
				finish();
				break;
			default:
				break;
		}
	}
}
