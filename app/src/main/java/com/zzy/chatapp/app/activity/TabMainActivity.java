package com.zzy.chatapp.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioGroup;
import com.zzy.chatapp.app.R;
import com.zzy.chatapp.app.adapter.FragmentTabAdapter;
import com.zzy.chatapp.app.fragment.ChatFragment;
import com.zzy.chatapp.app.fragment.HelpFragment;
import com.zzy.chatapp.app.fragment.MeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by justin on 3/2/16.
 */
public class TabMainActivity extends FragmentActivity {
	private RadioGroup rgs;
	public List<Fragment> fragments = new ArrayList<Fragment>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);

		fragments.add(new ChatFragment());
		fragments.add(new HelpFragment());
		fragments.add(new MeFragment());

		rgs = (RadioGroup) findViewById(R.id.tabs_rg);

		FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this, fragments, R.id.tab_content, rgs);

	}
}
