package com.zzy.chatapp.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.zzy.chatapp.app.R;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_edit);

		initViews();
		setTestview();
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

	void setTestview() {
		Bundle bundle = getIntent().getExtras();

		etHelpDetails.setText(bundle.getString("details"));
		etHelpDetails.setFocusable(false);
		etHelpDetails.setFocusableInTouchMode(false);

		etHelpMoney.setText("0");
		etHelpMoney.setFocusable(false);
		etHelpMoney.setFocusableInTouchMode(false);

		etHelpAddress.setText("星湖街213号123栋132室");
		etHelpAddress.setFocusable(false);
		etHelpAddress.setFocusableInTouchMode(false);

		etHelpName.setText("老王");
		etHelpName.setFocusable(false);
		etHelpName.setFocusableInTouchMode(false);

		etHelpPhone.setText("12312345612");
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
}
