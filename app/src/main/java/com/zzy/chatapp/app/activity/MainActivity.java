package com.zzy.chatapp.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.zzy.chatapp.app.R;

public class MainActivity extends Activity implements View.OnClickListener{
	Button btnLogin;
	EditText etLoginName;
	EditText etLoginPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();

		btnLogin.setOnClickListener(this);
	}

	void initViews() {
		btnLogin = (Button) findViewById(R.id.btn_login);
		etLoginName = (EditText) findViewById(R.id.et_login_name);
		etLoginPassword = (EditText) findViewById(R.id.et_login_password);
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
			default:
				break;
		}
	}
}
