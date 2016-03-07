package com.zzy.chatapp.app.fragment;

import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zzy.chatapp.app.R;

/**
 * Created by justin on 3/2/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MeFragment extends Fragment {
	TextView tvTitleName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_me, null);

		initView(view);
		tvTitleName.setText(R.string.me);

		return view;
	}

	void initView(View view) {
		tvTitleName = (TextView) view.findViewById(R.id.tv_title_name);
	}
}
