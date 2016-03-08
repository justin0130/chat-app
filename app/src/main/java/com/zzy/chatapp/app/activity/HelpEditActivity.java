package com.zzy.chatapp.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.zzy.chatapp.app.R;

/**
 * Created by justin on 3/8/16.
 */
public class HelpEditActivity extends Activity {
	Button btnSubmit;
	TextView tvTitleName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_edit);

		initViews();

		btnSubmit.setVisibility(View.VISIBLE);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(HelpEditActivity.this, "暂时不提供发帖", Toast.LENGTH_LONG).show();
			}
		});
		tvTitleName.setText(R.string.edit);
	}

	void initViews() {
		btnSubmit = (Button) findViewById(R.id.btn_help_submit);
		tvTitleName = (TextView) findViewById(R.id.tv_title_name);
	}
}
