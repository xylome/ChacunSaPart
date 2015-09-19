package org.eu.fr.placard.chacunsapartsdk.lib;

import org.eu.fr.placard.chacunsapartsdk.beans.Group;
import org.eu.fr.placard.chacunsapartsdk.exceptions.BackendException;
import org.eu.fr.placard.chacunsapartsdk.exceptions.BadCredentialsException;
import org.eu.fr.placard.chacunsapartsdk.exceptions.HttpConnectionException;
import org.eu.fr.placard.chacunsapartsdk.R;
import org.eu.fr.placard.chacunsapartsdk.beans.BackendObject;
import org.eu.fr.placard.chacunsapartsdk.beans.Groups;
import org.eu.fr.placard.chacunsapartsdk.beans.LoginResponse;
import org.eu.fr.placard.chacunsapartsdk.conf.BackendConf;
import org.eu.fr.placard.chacunsapartsdk.listeners.BackendListener;
import org.eu.fr.placard.chacunsapartsdk.data.DataManager;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class Backend implements BackendConf {

	private static final String TAG = Backend.class.getSimpleName();
	
	private String mUrl;
	
	private Context mContext;
	
	private DataManager mData;
	
	private static Backend mInstance;
	
	private Backend(Context c) {
		mContext = c;
		mData = DataManager.getInstance(c);
		mUrl = mContext.getString(R.string.csp_url);	
	}
	
	
	public static Backend getInstance(Context c) {
		if(mInstance == null) {
			mInstance = new Backend(c);
		}
		return mInstance;
	}
	
	public void login(BackendListener caller, String login, String password) {
		Log.d(TAG, "login with:" + login);
		final BackendListener lcaller = caller;
		String params = BackendQuery.buildLoginParams(login, password);
		
		if(TextUtils.isEmpty(params)) {
			lcaller.onBackendError(null);
			return;
		}
		
		BackendQuery bq = new BackendQuery(mUrl, null, ACTION_LOGIN, params);
		BackendAsync ba = new BackendAsync() {
			@Override
			protected void onPostExecute(String result) {	
				LoginResponse lr = null;
				Log.d(TAG, "login: received result: " + result);
				
				if (result == null) {
					lcaller.onBackendError(new HttpConnectionException("Error while retrieving data from Http connection"));
					return;
				}
				
				String data = extractDataFromResponse(result);				

				try {
					Gson gson = new Gson();
					lr = gson.fromJson(data, LoginResponse.class);
				} catch (JsonParseException jpe) {
					Log.e(TAG, "login: JSON Exception" + jpe.getMessage());
					lcaller.onBackendError(new BadCredentialsException("Error while parsing json: " + jpe.getMessage()));
					return;
				}
				
				Log.d(TAG, "login: login response is exploitable");
				if (lr.isOk()) {
					saveInfo(lr);
					lcaller.onBackendResponse(lr);
				} else {
					Log.e(TAG, "login : BAD CREDENTIALS");
					logout();
					lcaller.onBackendError(new BadCredentialsException("Bad credentials"));
				}
			}
		};		
		ba.execute(bq);
	}

	public void getExpenses(final BackendListener caller, int group_id) {
        Log.d(TAG, "getExpenses: begin.");

        String params = BackendQuery.buildGetExpensesParams(group_id);

        if(TextUtils.isEmpty(params)) {
            caller.onBackendError(null);
            return;
        }

        BackendQuery bq = new BackendQuery(mUrl, mData.getCookie(), ACTION_GET_EXPENSES, params);
        BackendAsync ba = new BackendAsync() {
            @Override
            protected void onPostExecute(String s) {
                BackendObject bo = null;
                Log.d(TAG, "getExpense: result: " + s);

                if(s == null) {
                    caller.onBackendError(new HttpConnectionException("Error HTTP while retrieving expenses."));
                    return;
                }

                try {
                    Gson gson = new Gson();
                    bo = gson.fromJson(s, Groups.class);
                } catch(JsonParseException e) {
                    Log.e(TAG, "Error while parsing JSON: " + e.getMessage());
                    caller.onBackendError(new BackendException("Problem while parsing server expenses"));
                    return;
                }
                caller.onBackendResponse(bo);
            }
        };
    }
	
	public void myGroups(BackendListener caller) 
	{
		Log.d(TAG, "MyGroups : begin.");
		final BackendListener lcaller = caller;
		
		String params = BackendQuery.buildMyGroupsParams(mData.getAccountId());
		if(TextUtils.isEmpty(params)) {
			lcaller.onBackendError(null);
			return;
		}
		
		BackendQuery bq = new BackendQuery(mUrl, mData.getCookie(), ACTION_MYGROUPS, params);
		BackendAsync ba = new BackendAsync() {
			@Override
			protected void onPostExecute(String result) {	
				BackendObject bo = null;
				Log.d(TAG, "myGroups: result: " + result);
				
				if (result == null) {
					lcaller.onBackendError(new HttpConnectionException("Error while retrieving data from Http connection"));
					return;
				}
				
				//String data = extractDataFromResponse(result);				

				try {
					Gson gson = new Gson();
					bo = gson.fromJson(result, Groups.class);
				} catch (JsonParseException jpe) {
					Log.e(TAG, "login: JSON Exception" + jpe.getMessage());
					lcaller.onBackendError(new BadCredentialsException("Error while parsing json: " + jpe.getMessage()));
					return;
				}
				lcaller.onBackendResponse(bo);
			}
		};		
		ba.execute(bq);
	}
	
	public void logout() {
		mData.writeAccountId(null);
		mData.writeNick(null);
		mData.writeActorId(null);
		// keeping email in sharedPrefs.
		//mData.writeEmail(null); 
		mData.writeCookie(null);
	}
	
	private void saveInfo(LoginResponse lr) {
		mData.writeAccountId(lr.getAccountId());
		mData.writeNick(lr.getNick());
		mData.writeActorId(lr.getActorId());
		mData.writeEmail(lr.getEmail());
		mData.writeCookie(lr.getAuthCookie());
	}
	
	private String extractDataFromResponse(String json) {
		String result = "";
		
		try {
			JSONObject jso = new JSONObject(json);
			result = jso.getString("data");
		} catch (JSONException e) {
			Log.e(TAG, "Error JSON: " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
	
	public boolean isLoggedIn() {
		return !TextUtils.isEmpty(mData.getCookie());
	}
	
	public String getNick() {
		return mData.getNick();
	}
	
	public String getEmail() {
		return mData.getEmail();
	}
}
