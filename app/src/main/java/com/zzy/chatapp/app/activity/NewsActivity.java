package com.zzy.chatapp.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.zzy.chatapp.app.R;
import com.zzy.chatapp.app.adapter.NewsListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by justin on 3/8/16.
 */
public class NewsActivity extends Activity {
	ListView lvNews;
	TextView tvTitleName;
	List<HashMap<String, Object>> list;
	NewsListViewAdapter newsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);

		initViews();

		list = new ArrayList<HashMap<String, Object>>();
		for(int i=0; i<5; i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("content", "新消息" + (i + 1));
			list.add(map);
		}
		newsAdapter = new NewsListViewAdapter(this, list);
		lvNews.setAdapter(newsAdapter);
		lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent;
				Bundle bundle;

				intent = new Intent(NewsActivity.this, HelpDetailsActivity.class);
				bundle = new Bundle();
				bundle.putString("details", ((TextView) view.findViewById(R.id.tv_newslist_content)).getText().toString());
				bundle.putString("flag", "news");
				intent.putExtras(bundle);

				startActivity(intent);
			}
		});

		tvTitleName.setText(R.string.news_news);
	}

	void initViews() {
		lvNews = (ListView) findViewById(R.id.lv_news);
		tvTitleName = (TextView) findViewById(R.id.tv_title_name);
	}
}
