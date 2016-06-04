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
import android.widget.*;
import com.zzy.chatapp.app.R;
import com.zzy.chatapp.app.tools.HttpUtils;
import com.zzy.chatapp.app.tools.OnLoadDialog;
import com.zzy.chatapp.app.tools.RequestServerUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by justin on 6/2/16.
 */
public class ToolsActivity extends Activity implements View.OnClickListener {
	ImageView ivTitleBack;
	TextView tvTitleName;
	Button btnToolsSetTime;
	Button btnToolsSetTag;
	Button btnToolsShowTags;
	Button btnToolsFeedback;
	Button btnLogout;

	OnLoadDialog onLoadDialog;
	Handler feedbackHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.obj == null) {
				onLoadDialog.cancel();
				Toast.makeText(ToolsActivity.this,
						R.string.submit_failure, Toast.LENGTH_SHORT).show();
				return;
			}
			String json = msg.obj.toString();
			if(HttpUtils.TIME_OUT.equals(json)) {
				onLoadDialog.cancel();
				Toast.makeText(ToolsActivity.this,
						R.string.connect_time_out, Toast.LENGTH_SHORT).show();
				return;
			}
			try {
				JSONObject object = new JSONObject(json);
				String status = object.getString("status");
				if("0".equals(status)) {
					onLoadDialog.cancel();
					Toast.makeText(ToolsActivity.this,
							R.string.submit_failure, Toast.LENGTH_SHORT).show();
					return;
				}
				onLoadDialog.cancel();
				Toast.makeText(ToolsActivity.this,
						R.string.submit_success, Toast.LENGTH_SHORT).show();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tools);

		initViews();

		ivTitleBack.setVisibility(View.VISIBLE);
		tvTitleName.setText(R.string.tools);

		ivTitleBack.setOnClickListener(this);
		btnToolsSetTime.setOnClickListener(this);
		btnToolsSetTag.setOnClickListener(this);
		btnToolsShowTags.setOnClickListener(this);
		btnToolsFeedback.setOnClickListener(this);
		btnLogout.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.iv_title_back:
				finish();
				break;
			case R.id.btn_tools_set_time:
				break;
			case R.id.btn_tools_set_tag:
				break;
			case R.id.btn_tools_show_tags:
				break;
			case R.id.btn_tools_feedback:
				sendFeedback();
				break;
			case R.id.btn_logout:
				Intent intent = new Intent(ToolsActivity.this, LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;
			default:
				break;
		}
	}

	private void initViews() {
		ivTitleBack = (ImageView) findViewById(R.id.iv_title_back);
		tvTitleName = (TextView) findViewById(R.id.tv_title_name);
		btnToolsSetTime = (Button) findViewById(R.id.btn_tools_set_time);
		btnToolsSetTag = (Button) findViewById(R.id.btn_tools_set_tag);
		btnToolsShowTags = (Button) findViewById(R.id.btn_tools_show_tags);
		btnToolsFeedback = (Button) findViewById(R.id.btn_tools_feedback);
		btnLogout = (Button) findViewById(R.id.btn_logout);
	}

	private void sendFeedback() {
		final View view = LayoutInflater
				.from(ToolsActivity.this).inflate(R.layout.item_feedback, null);
		new AlertDialog.Builder(ToolsActivity.this)
				.setTitle(R.string.please_input_ip_and_port)
				.setView(view)
				.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						EditText etFeedbackTitle =
								(EditText) view.findViewById(R.id.et_feedback_title);
						EditText etFeedbackContent =
								(EditText) view.findViewById(R.id.et_feedback_content);
						onLoadDialog = new OnLoadDialog(ToolsActivity.this);
						onLoadDialog.show();
						RequestServerUtils.addFeedback(
								feedbackHandler,
								etFeedbackTitle.getText().toString(),
								etFeedbackContent.getText().toString()
						);
					}
				})
				.setNegativeButton(R.string.cancel, null)
				.create()
				.show();
	}
}
