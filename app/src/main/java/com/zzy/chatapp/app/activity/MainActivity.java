package com.zzy.chatapp.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.zzy.chatapp.app.R;
import com.zzy.chatapp.app.tools.RequestServerUtils;

public class MainActivity extends Activity implements View.OnClickListener{
	Button btnLogin;
	EditText etLoginName;
	EditText etLoginPassword;
	TextView tvRegister;

	Handler handler;
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();

		btnLogin.setOnClickListener(this);
		tvRegister.setOnClickListener(this);

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if(msg.obj == null) {
					pDialog.cancel();
					return;
				}
				pDialog.cancel();
				Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
			}
		};
	}

	void initViews() {
		btnLogin = (Button) findViewById(R.id.btn_login);
		etLoginName = (EditText) findViewById(R.id.et_login_name);
		etLoginPassword = (EditText) findViewById(R.id.et_login_password);
		tvRegister = (TextView) findViewById(R.id.tv_register);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_login:
				if(!"15162330809".equals(etLoginName.getText().toString()) || !"123456".equals(etLoginPassword.getText().toString())) {
					Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
					return;
				}
				Intent intent = new Intent(MainActivity.this, TabMainActivity.class);
				startActivity(intent);
				finish();
				break;
			case R.id.tv_register:
				RequestServerUtils.checkLogin(handler, etLoginName.getText().toString(), etLoginPassword.getText().toString());
				pDialog = new ProgressDialog(MainActivity.this);
				pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pDialog.setMessage("正在加载中...");
				pDialog.show();
				break;
			default:
				break;
		}
	}
}
