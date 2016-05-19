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

/**
 * Created by justin on 3/8/16.
 */
public class HelpDetailsActivity extends Activity {
	Button btnHelpSubmit;
	EditText etHelpDetails;
	EditText etHelpMoney;
	EditText etHelpAddress;
	EditText etHelpName;
	EditText etHelpPhone;
	TextView tvTitltName;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_edit);

		Bundle bundle = getIntent().getExtras();

		initViews();
		setViews(bundle);
		getContent(bundle);
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

	void setViews(Bundle bundle) {
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

		if("news".equals(bundle.getString("flag"))) {
			btnHelpSubmit.setVisibility(View.VISIBLE);
			btnHelpSubmit.setText(R.string.accept);
			btnHelpSubmit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Toast.makeText(HelpDetailsActivity.this, "暂时不提供接帖", Toast.LENGTH_LONG).show();
				}
			});
		}
	}

	private void getContent(Bundle bundle) {
		onLoadDialog = new OnLoadDialog(HelpDetailsActivity.this);
		onLoadDialog.show();
		RequestServerUtils.getPostDetails(contentHandler, bundle.getString("postId"));
	}

	private void setPostDetails(JSONObject content) throws JSONException {
		etHelpDetails.setText(content.getString("content"));
		etHelpMoney.setText(content.getString("reward"));
		etHelpAddress.setText(content.getString("address"));
		etHelpName.setText(content.getString("nickName"));
		etHelpPhone.setText(content.getString("phone"));
	}
}
