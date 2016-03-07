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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.zzy.chatapp.app.R;
import com.zzy.chatapp.app.activity.NewsActivity;
import com.zzy.chatapp.app.adapter.NewsListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by justin on 3/2/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ChatFragment extends Fragment {
	ListView lvChat;
	TextView tvTitleName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_chat, null);

		initView(view);

		List<HashMap<String, Object>> list = testData();
		lvChat.setAdapter(new NewsListViewAdapter(getActivity(), list));
		lvChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent;
				Bundle bundle;

				intent = new Intent(getActivity(), NewsActivity.class);
				bundle = new Bundle();
				bundle.putString("name", ((TextView)view.findViewById(R.id.tv_newslist_name)).getText().toString());
				intent.putExtras(bundle);

				startActivity(intent);
			}
		});

		tvTitleName.setText(R.string.news);

		return view;
	}

	void initView(View view) {
		lvChat = (ListView) view.findViewById(R.id.lv_chat);
		tvTitleName = (TextView) view.findViewById(R.id.tv_title_name);
	}

	List<HashMap<String, Object>> testData() {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("name", "张三");
		map1.put("news", "求救啊");
		map1.put("time", "8:00");
		list.add(map1);

		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("name", "李四");
		map2.put("news", "请人搬桌子");
		map2.put("time", "8:01");
		list.add(map2);

		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("name", "王五");
		map3.put("news", "能帮我带带小狗baby吗");
		map3.put("time", "8:02");
		list.add(map3);

		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("name", "小李子");
		map4.put("news", "来吧，来吧");
		map4.put("time", "8:03");
		list.add(map4);

		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("name", "老王");
		map5.put("news", "我是老王");
		map5.put("time", "8:04");
		list.add(map5);

		HashMap<String, Object> map6 = new HashMap<String, Object>();
		map6.put("name", "老赵");
		map6.put("news", "我是老赵");
		map6.put("time", "8:05");
		list.add(map6);

		HashMap<String, Object> map7 = new HashMap<String, Object>();
		map7.put("name", "老吴");
		map7.put("news", "我是老吴");
		map7.put("time", "8:06");
		list.add(map7);

		return list;
	}
}
