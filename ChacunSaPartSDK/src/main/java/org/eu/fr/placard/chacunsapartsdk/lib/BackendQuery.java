package org.eu.fr.placard.chacunsapartsdk.lib;

import org.eu.fr.placard.chacunsapartsdk.conf.BackendConf;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class BackendQuery implements BackendConf {

	private static final String TAG = BackendQuery.class.getSimpleName();
	private String mCookie;
	private String mAction;
	private String mParams;
	private String mUrl;

	public BackendQuery(String mUrl, String mCookie, String mAction,
			String mParams) {

		this.mCookie = mCookie;
		this.mAction = mAction;
		this.mParams = mParams;
		this.mUrl = mUrl;
	}

	public String getCookie() {
		return mCookie;
	}

	public void setCookie(String mCookie) {
		this.mCookie = mCookie;
	}

	public String getAction() {
		return mAction;
	}

	public void setAction(String mAction) {
		this.mAction = mAction;
	}

	public String getParams() {
		return mParams;
	}

	public void setParams(String mParams) {
		this.mParams = mParams;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String mUrl) {
		this.mUrl = mUrl;
	}

	public static String buildLoginParams(String login, String password) {
		String result = null;

		JSONObject obj = new JSONObject();
		try {
			obj.put(FIELD_EMAIL, login);
			obj.put(FIELD_PASSWORD, password);
			result = obj.toString();
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
		}
		return result;
	}

	public static String buildMyGroupsParams(String account_id) {
		String result = null;

		JSONObject obj = new JSONObject();
		try {
			obj.put(FIELD_ACCOUNT_ID, account_id);
			result = obj.toString();
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
		}
		return result;
	}

}
