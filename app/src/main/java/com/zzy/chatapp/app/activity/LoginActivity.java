package com.zzy.chatapp.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.zzy.chatapp.app.R;
import com.zzy.chatapp.app.tools.HttpUtils;
import com.zzy.chatapp.app.tools.OnLoadDialog;
import com.zzy.chatapp.app.tools.RequestServerUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity implements View.OnClickListener{
	Button btnLogin;
	EditText etLoginName;
	EditText etLoginPassword;
	TextView tvRegister;
	TextView tvSetIpAndPort;

	OnLoadDialog onLoadDialog;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.obj == null) {
				onLoadDialog.cancel();
				Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
				return;
			}
			String json = msg.obj.toString();
			if(HttpUtils.TIME_OUT.equals(json)) {
				onLoadDialog.cancel();
				Toast.makeText(LoginActivity.this, R.string.connect_time_out, Toast.LENGTH_SHORT).show();
				return;
			}
			try {
				JSONObject object = new JSONObject(json);
				String status = object.getString("status");
				if("0".equals(status)) {
					onLoadDialog.cancel();
					Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
					return;
				}
				//set user id
				String userId = object.getString("userId");
				RequestServerUtils.setUserId(userId);

				onLoadDialog.cancel();
				Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
				startActivity(new Intent(LoginActivity.this, TabMainActivity.class));
				finish();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initViews();
		btnLogin.setOnClickListener(this);
		tvRegister.setOnClickListener(this);
		tvSetIpAndPort.setOnClickListener(this);
	}

	private void initViews() {
		btnLogin = (Button) findViewById(R.id.btn_login);
		etLoginName = (EditText) findViewById(R.id.et_login_name);
		etLoginPassword = (EditText) findViewById(R.id.et_login_password);
		tvRegister = (TextView) findViewById(R.id.tv_register);
		tvSetIpAndPort = (TextView) findViewById(R.id.tv_set_ip_and_port);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_login:
				if("".equals(RequestServerUtils.IP) || "".equals(RequestServerUtils.PORT)) {
					Toast.makeText(LoginActivity.this,
							R.string.please_input_ip_and_port, Toast.LENGTH_SHORT).show();
					return;
				}
				if("".equals(etLoginName.getText().toString()) ||
						"".equals(etLoginPassword.getText().toString())) {
					Toast.makeText(LoginActivity.this,
							R.string.please_input_login, Toast.LENGTH_SHORT).show();
					return;
				}
				onLoadDialog = new OnLoadDialog(LoginActivity.this);
				onLoadDialog.show();
				RequestServerUtils.login(handler,
						etLoginName.getText().toString(),
						etLoginPassword.getText().toString());
				break;
			case R.id.tv_register:
				startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
				break;
			case R.id.tv_set_ip_and_port:
				setIpAndPort();
				break;
			default:
				break;
		}
	}

	private void setIpAndPort() {
		final View view = LayoutInflater.from(this).inflate(R.layout.item_set_ip_and_port, null);
		new AlertDialog.Builder(LoginActivity.this)
				.setTitle(R.string.please_input_ip_and_port)
				.setView(view)
				.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						EditText etIp = (EditText) view.findViewById(R.id.et_set_ip);
						EditText etPort = (EditText) view.findViewById(R.id.et_set_port);
						RequestServerUtils.setBaseUrl(
								etIp.getText().toString(), etPort.getText().toString());
					}
				})
				.setNegativeButton(R.string.cancel, null)
				.create()
				.show();
	}
}
