package com.zzy.chatapp.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.zzy.chatapp.app.R;

/**
 * Created by justin on 3/3/16.
 */
public class NewsActivity extends Activity implements View.OnClickListener {
	private ImageView ivTitleBack;
	private TextView tvTitleName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);

		initViews();

		ivTitleBack.setVisibility(View.VISIBLE);
		ivTitleBack.setOnClickListener(this);

		Bundle bundle = getIntent().getExtras();

		tvTitleName.setText(bundle.getString("name"));
	}

	private void initViews() {
		ivTitleBack = (ImageView) findViewById (R.id.iv_title_back);
		tvTitleName = (TextView) findViewById (R.id.tv_title_name);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.iv_title_back:
				finish();
			default:
				break;
		}
	}
}
