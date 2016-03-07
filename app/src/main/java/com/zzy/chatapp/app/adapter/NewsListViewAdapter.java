package com.zzy.chatapp.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zzy.chatapp.app.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by justin on 3/4/16.
 */
public class NewsListViewAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflater;
	private List<HashMap<String, Object>> list;

	public NewsListViewAdapter(Context context, List<HashMap<String, Object>> list) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int i) {
		return null;
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public View getView(int i, View convertView, ViewGroup viewGroup) {
		ViewHolder holder = null;

		if(convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_newslist, null);
			holder.tvNewsListName = (TextView) convertView.findViewById(R.id.tv_newslist_name);
			holder.tvNewsListNews = (TextView) convertView.findViewById(R.id.tv_newslist_news);
			holder.tvNewsListTime = (TextView) convertView.findViewById(R.id.tv_newslist_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		HashMap<String, Object> map = list.get(i);
		holder.tvNewsListName.setText(map.get("name").toString());
		holder.tvNewsListNews.setText(map.get("news").toString());
		holder.tvNewsListTime.setText(map.get("time").toString());

		return convertView;
	}

	class ViewHolder {
		ImageView ivNewsList;
		TextView tvNewsListName;
		TextView tvNewsListNews;
		TextView tvNewsListTime;
	}
}
