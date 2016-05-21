package com.zzy.chatapp.app.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.zzy.chatapp.app.R;
import com.zzy.chatapp.app.activity.ChatActivity;
import com.zzy.chatapp.app.activity.HelpDetailsActivity;
import com.zzy.chatapp.app.activity.NewsActivity;
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

	List<HashMap<String, Object>> newsList = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_chat, null);

		initViews(view);
		tvTitleName.setText(R.string.news);

		generateTestData();
		lvChat.setAdapter(new ChatListViewAdapter(getActivity(), newsList));

		lvChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent;
				Bundle bundle;

				intent = new Intent(getActivity(), HelpDetailsActivity.class);
				bundle = new Bundle();
				bundle.putString("flag", "news");
				bundle.putString("postId", newsList.get(i).get("postId").toString());
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

	void generateTestData() {
		newsList = new ArrayList<HashMap<String, Object>>();

		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("postId", "1");
		map1.put("name", "张三");
		map1.put("news", "求救啊");
		map1.put("time", "8:00");
		newsList.add(map1);

		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("postId", "2");
		map2.put("name", "李四");
		map2.put("news", "请人搬桌子");
		map2.put("time", "8:01");
		newsList.add(map2);

		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("postId", "3");
		map3.put("name", "王五");
		map3.put("news", "能帮我带带小狗baby吗");
		map3.put("time", "8:02");
		newsList.add(map3);
	}
}
