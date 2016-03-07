package com.zzy.chatapp.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.zzy.chatapp.app.R;

public class MainActivity extends Activity implements View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btn = (Button) findViewById(R.id.jumpToNextActivity);
		btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.jumpToNextActivity:
				Intent intent = new Intent(MainActivity.this, TabMainActivity.class);
				startActivity(intent);
				finish();
				break;
			default:
				break;
		}
	}
}
