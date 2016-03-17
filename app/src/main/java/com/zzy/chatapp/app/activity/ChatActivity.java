package com.zzy.chatapp.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.zzy.chatapp.app.R;
import com.zzy.chatapp.app.tools.RequestServerUtils;

/**
 * Created by justin on 3/3/16.
 */
public class ChatActivity extends Activity implements View.OnClickListener {
	private Button btnTest;
	private ImageView ivTitleBack;
	private TextView tvTest;
	private TextView tvTitleName;

	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		initViews();

		ivTitleBack.setVisibility(View.VISIBLE);
		ivTitleBack.setOnClickListener(this);

		Bundle bundle = getIntent().getExtras();

		btnTest.setOnClickListener(this);
		tvTitleName.setText(bundle.getString("name"));

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if(msg.obj == null) {
					return;
				}
				tvTest.setText(msg.obj.toString());
			}
		};
	}

	private void initViews() {
		btnTest = (Button) findViewById (R.id.btn_test);
		ivTitleBack = (ImageView) findViewById (R.id.iv_title_back);
		tvTest = (TextView) findViewById (R.id.tv_test);
		tvTitleName = (TextView) findViewById (R.id.tv_title_name);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.iv_title_back:
				finish();
				break;
			case R.id.btn_test:
				RequestServerUtils.getServerData(handler);
				break;
			default:
				break;
		}
	}
}
