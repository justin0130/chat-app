package com.zzy.chatapp.app.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.zzy.chatapp.app.R;
import com.zzy.chatapp.app.activity.HelpDetailsActivity;
import com.zzy.chatapp.app.adapter.ChatListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by justin on 3/2/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ChatFragment extends Fragment {
	ListView lvChat;
	TextView tvNews;
	TextView tvTitleName;

	public static List<HashMap<String, Object>> newsList = null;
	static ChatListViewAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
		Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_chat, null);

		initViews(view);
		tvTitleName.setText(R.string.news);

		if(newsList == null) {
			newsList = new ArrayList<HashMap<String, Object>>();
		}
		adapter = new ChatListViewAdapter(getActivity(), newsList);
		lvChat.setAdapter(adapter);

		lvChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent;
				Bundle bundle;

				intent = new Intent(getActivity(), HelpDetailsActivity.class);
				bundle = new Bundle();
				bundle.putString("flag", "news");
				int no = newsList.size()-i-1;
				bundle.putString("postId", newsList.get(no).get("postId").toString());
				intent.putExtras(bundle);

				startActivity(intent);
			}
		});

		return view;
	}

	void initViews(View view) {
		lvChat = (ListView) view.findViewById(R.id.lv_chat);
		tvNews = (TextView) view.findViewById(R.id.tv_news);
		tvTitleName = (TextView) view.findViewById(R.id.tv_title_name);
	}

	public static void refresh(HashMap<String, Object> map) {
		if(newsList == null) {
			newsList = new ArrayList<HashMap<String, Object>>();
		}
		newsList.add(map);
		adapter.notifyDataSetChanged();
	}
}
