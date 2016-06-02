package com.zzy.chatapp.app.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.zzy.chatapp.app.R;
import com.zzy.chatapp.app.activity.HelpDetailsActivity;
import com.zzy.chatapp.app.activity.HelpEditActivity;
import com.zzy.chatapp.app.adapter.HelpListViewAdapter;
import com.zzy.chatapp.app.tools.HttpUtils;
import com.zzy.chatapp.app.tools.OnLoadDialog;
import com.zzy.chatapp.app.tools.RequestServerUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by justin on 3/2/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class HelpFragment extends Fragment implements View.OnClickListener {
	ListView lvHelp;
	ImageView ivTitleAdd;
	TextView tvTitleName;
	static HelpListViewAdapter adapter;

	static List<List> data = null;
	static List<HashMap<String, Object>> postList = null;
	static List<HashMap<String, Object>> helpList = null;

	OnLoadDialog onLoadDialog;
	Handler helpHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.obj == null) {
				onLoadDialog.cancel();
				Toast.makeText(getActivity(), R.string.no_help_list, Toast.LENGTH_SHORT).show();
				return;
			}
			String obj = msg.obj.toString();
			if(HttpUtils.TIME_OUT.equals(obj)) {
				onLoadDialog.cancel();
				Toast.makeText(getActivity(), R.string.connect_time_out, Toast.LENGTH_SHORT).show();
				return;
			}
			try {
				JSONObject json = new JSONObject(obj);
				String status = json.getString("status");
				if("0".equals(status)) {
					onLoadDialog.cancel();
					Toast.makeText(getActivity(), R.string.no_help_list, Toast.LENGTH_SHORT).show();
					return;
				}
				setMyPostAndHelpList(json);
				onLoadDialog.cancel();
			} catch (JSONException e) {
				onLoadDialog.cancel();
				e.printStackTrace();
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_help, null);

		initViews(view);
		initList();
		getHelpList();

		ivTitleAdd.setVisibility(View.VISIBLE);
		ivTitleAdd.setOnClickListener(this);
		tvTitleName.setText(R.string.help);

		lvHelp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent;
				Bundle bundle;
				String postId = "";

				int categroyFirstIndex = 0;
				for (List list : data) {
					int size = list.size();
					int categoryIndex = i - categroyFirstIndex;
					if (categoryIndex == 0) {
						return;
					}
					if (categoryIndex < size) {
						postId = ((HashMap) list.get(categoryIndex)).get("postId").toString();
						break;
					}
					categroyFirstIndex = categroyFirstIndex + size;
				}
				intent = new Intent(getActivity(), HelpDetailsActivity.class);
				bundle = new Bundle();
				try {
					bundle.putString("flag", "details");
					bundle.putString("postId", postId);
					intent.putExtras(bundle);

					startActivity(intent);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
//		getHelpList();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.iv_title_add:
				startActivity(new Intent(getActivity(), HelpEditActivity.class));
				break;
			default:
				break;
		}
	}

	private void initViews(View view) {
		lvHelp = (ListView) view.findViewById(R.id.lv_help);
		ivTitleAdd = (ImageView) view.findViewById(R.id.iv_title_add);
		tvTitleName = (TextView) view.findViewById(R.id.tv_title_name);
	}

	private void initList() {
		initDateList();
		adapter = new HelpListViewAdapter(getActivity(), data);
		lvHelp.setAdapter(adapter);
	}

	static void initDateList() {
		data = new ArrayList<List>();
		postList = new ArrayList<HashMap<String, Object>>();
		helpList = new ArrayList<HashMap<String, Object>>();

		//add post list title
		HashMap<String, Object> mapPostTitle = new HashMap<String, Object>();
		mapPostTitle.put("title", "我的求助");
		postList.add(mapPostTitle);
		//add help list title
		HashMap<String, Object> mapHelpTitle = new HashMap<String, Object>();
		mapHelpTitle.put("title", "我的帮助");
		helpList.add(mapHelpTitle);

		data.add(postList);
		data.add(helpList);
	}

	private void getHelpList() {
		onLoadDialog = new OnLoadDialog(getActivity());
		onLoadDialog.show();
		RequestServerUtils.getHelpList(helpHandler);
	}

	private void setMyPostAndHelpList(JSONObject json) throws JSONException {
		JSONArray arrayPost = json.getJSONArray("myPost");
		JSONArray arrayHelp = json.getJSONArray("myHelp");

		//add post list content
		for(int i=0; i<arrayPost.length(); i++) {
			JSONObject object  = arrayPost.getJSONObject(i);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("postId", object.getString("postId"));
			map.put("nickName", object.getString("nickName"));
			map.put("content", object.getString("content"));
			postList.add(map);
		}
		//add help list content
		for(int i=0; i<arrayHelp.length(); i++) {
			JSONObject object  = arrayHelp.getJSONObject(i);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("postId", object.getString("postId"));
			map.put("nickName", object.getString("nickName"));
			map.put("content", object.getString("content"));
			helpList.add(map);
		}
		adapter.notifyDataSetChanged();
	}

	public static void refresh(HashMap<String, Object> map, String tag) {
		if(data == null) {
			initDateList();
		}
		if("post".equals(tag)) {
			postList.add(map);
		}
		if("help".equals(tag)) {
			helpList.add(map);
		}
		adapter.notifyDataSetChanged();
	}
}
