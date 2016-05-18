package com.zzy.chatapp.app.tools;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by justin on 4/16/16.
 */
public class OnLoadDialog {
	private ProgressDialog pDialog;
	private static int PROGRESS_DIALOG_STYLE = ProgressDialog.STYLE_SPINNER;
	private static String ON_LOAD = "正在加载中...";

	public OnLoadDialog(Context context) {
		pDialog = new ProgressDialog(context);
		pDialog.setProgressStyle(PROGRESS_DIALOG_STYLE);
		pDialog.setMessage(ON_LOAD);
	}

	public void show() {
		pDialog.show();
	}

	public void cancel() {
		pDialog.cancel();
	}
}
