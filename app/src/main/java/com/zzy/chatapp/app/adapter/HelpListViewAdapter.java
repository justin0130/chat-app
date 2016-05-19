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
public class HelpListViewAdapter extends BaseAdapter {
	private static final int TYPE_CATEGORY_ITEM = 0;
	private static final int TYPE_ITEM = 1;

	private Context context;
	private LayoutInflater mInflater;
	private List<List> mList;

	public HelpListViewAdapter(Context context, List<List> list) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.mList = list;
	}

	@Override
	public int getCount() {
		int count = 0;

		if (null != mList) {
			for (List list : mList) {
				count += list.size();
			}
		}

		return count;
	}

	@Override
	public Object getItem(int position) {
		int categroyFirstIndex;

		if (null == mList || position <  0|| position > getCount()) {
			return null;
		}
		categroyFirstIndex = 0;
		for (List list : mList) {
			int size = list.size();
			int categoryIndex = position - categroyFirstIndex;

			if (categoryIndex < size) {
				return  list.get(categoryIndex);
			}
			categroyFirstIndex += size;
		}
		return null;
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public int getItemViewType(int position) {
		int categroyFirstIndex;

		if (null == mList || position <  0|| position > getCount()) {
			return TYPE_ITEM;
		}
		categroyFirstIndex = 0;
		for (List list : mList) {
			int size = list.size();
			int categoryIndex = position - categroyFirstIndex;

			if (categoryIndex == 0) {
				return TYPE_CATEGORY_ITEM;
			}
			categroyFirstIndex += size;
		}
		return TYPE_ITEM;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		int itemViewType;
		HashMap map;

		itemViewType = getItemViewType(position);
		switch (itemViewType) {
			case TYPE_CATEGORY_ITEM:
				convertView = mInflater.inflate(R.layout.item_helplist_header, null);
				TextView textView = (TextView) convertView.findViewById(R.id.tv_helplist_category);
				map = (HashMap) getItem(position);
				textView.setText(map.get("title").toString());
				break;

			case TYPE_ITEM:
				convertView = mInflater.inflate(R.layout.item_helplist, null);
				TextView tvHelpListTitle = (TextView) convertView.findViewById(R.id.tv_helplist_title);
				map = (HashMap) getItem(position);
				tvHelpListTitle.setText(map.get("content").toString());
				break;
		}

		return convertView;
	}
}
