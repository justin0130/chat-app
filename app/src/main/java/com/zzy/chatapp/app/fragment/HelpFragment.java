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
	List<List> data = null;
	List<HashMap<String, Object>> postList;
	List<HashMap<String, Object>> helpList;

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
				for(List list : data) {
					int size = list.size();
					int categoryIndex = i - categroyFirstIndex;
					if(categoryIndex == 0) {
						return;
					}
					if(categoryIndex < size) {
						postId = ((HashMap)list.get(categoryIndex)).get("postId").toString();
						break;
					}
					categroyFirstIndex = categroyFirstIndex + size;
				}
				intent = new Intent(getActivity(), HelpDetailsActivity.class);
				bundle = new Bundle();
				try {
					bundle.putString("postId", postId);
					bundle.putString("flag", "details");
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
		System.out.println("````````````````` on resume `````````````````");
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

	void initViews(View view) {
		lvHelp = (ListView) view.findViewById(R.id.lv_help);
		ivTitleAdd = (ImageView) view.findViewById(R.id.iv_title_add);
		tvTitleName = (TextView) view.findViewById(R.id.tv_title_name);
	}

	private void getHelpList() {
		onLoadDialog = new OnLoadDialog(getActivity());
		onLoadDialog.show();
		RequestServerUtils.getHelpList(helpHandler);
	}

	private void setMyPostAndHelpList(JSONObject json) throws JSONException {
		JSONArray arrayPost = json.getJSONArray("myPost");
		JSONArray arrayHelp = json.getJSONArray("myHelp");
		data = new ArrayList<List>();
		postList = new ArrayList<HashMap<String, Object>>();
		helpList = new ArrayList<HashMap<String, Object>>();

		//add post list title
		HashMap<String, Object> mapPostTitle = new HashMap<String, Object>();
		mapPostTitle.put("title", getResources().getString(R.string.my_post));
		postList.add(mapPostTitle);
		//add post list content
		for(int i=0; i<arrayPost.length(); i++) {
			JSONObject object  = arrayPost.getJSONObject(i);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("postId", object.getString("postId"));
			map.put("nickName", object.getString("nickName"));
			map.put("content", object.getString("content"));
			postList.add(map);
		}
		//add help list title
		HashMap<String, Object> mapHelpTitle = new HashMap<String, Object>();
		mapHelpTitle.put("title", getResources().getString(R.string.my_help));
		helpList.add(mapHelpTitle);
		//add help list content
		for(int i=0; i<arrayHelp.length(); i++) {
			JSONObject object  = arrayHelp.getJSONObject(i);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("postId", object.getString("postId"));
			map.put("nickName", object.getString("nickName"));
			map.put("content", object.getString("content"));
			helpList.add(map);
		}
		data.add(postList);
		data.add(helpList);
		lvHelp.setAdapter(new HelpListViewAdapter(getActivity(), data));
	}

	List<List> testData() {
		List<List> list = new ArrayList<List>();

		List<HashMap<String, Object>> listMyNeed = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map0 = new HashMap<String, Object>();
		map0.put("title", "我的求助");
		listMyNeed.add(map0);

		HashMap<String, Object> mapNeed1 = new HashMap<String, Object>();
		mapNeed1.put("content", "求助帮忙收拾东西");
		listMyNeed.add(mapNeed1);

		HashMap<String, Object> mapNeed2 = new HashMap<String, Object>();
		mapNeed2.put("content", "来找我玩");
		listMyNeed.add(mapNeed2);

		HashMap<String, Object> mapNeed3 = new HashMap<String, Object>();
		mapNeed3.put("content", "水管坏了ToT");
		listMyNeed.add(mapNeed3);

		HashMap<String, Object> mapNeed4 = new HashMap<String, Object>();
		mapNeed4.put("content", "找人修电脑");
		listMyNeed.add(mapNeed4);

		HashMap<String, Object> mapNeed5 = new HashMap<String, Object>();
		mapNeed5.put("content", "我是程序猿");
		listMyNeed.add(mapNeed5);

		List<HashMap<String, Object>> listMyHelp = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> mapHelp0 = new HashMap<String, Object>();
		mapHelp0.put("title", "我的帮助");
		listMyHelp.add(mapHelp0);

		HashMap<String, Object> mapHelp1 = new HashMap<String, Object>();
		mapHelp1.put("content", "帮忙除草");
		listMyHelp.add(mapHelp1);

		HashMap<String, Object> mapHelp2 = new HashMap<String, Object>();
		mapHelp2.put("content", "帮忙搬家");
		listMyHelp.add(mapHelp2);

		HashMap<String, Object> mapHelp3 = new HashMap<String, Object>();
		mapHelp3.put("content", "帮老奶奶拿快递");
		listMyHelp.add(mapHelp3);

		HashMap<String, Object> mapHelp4 = new HashMap<String, Object>();
		mapHelp4.put("content", "帮人转系统");
		listMyHelp.add(mapHelp4);

		HashMap<String, Object> mapHelp5 = new HashMap<String, Object>();
		mapHelp5.put("content", "我是程序猿");
		listMyHelp.add(mapHelp5);

		list.add(listMyNeed);
		list.add(listMyHelp);

		return list;
	}
}
