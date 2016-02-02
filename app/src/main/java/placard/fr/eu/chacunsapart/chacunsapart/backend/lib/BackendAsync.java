package placard.fr.eu.chacunsapart.chacunsapart.backend.lib;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import placard.fr.eu.chacunsapart.chacunsapart.backend.conf.BackendConf;

public class BackendAsync extends AsyncTask<BackendQuery, Void, String> implements BackendConf {

	private static final String TAG = BackendAsync.class.getSimpleName();

	@Override
	protected String doInBackground (BackendQuery... bqs) {
		Log.d(TAG, "doInBackground: begin");
		String result = "";
		URL url = null;
		HttpURLConnection urlConnection = null;
		InputStream in = null;

		try {
			url = new URL(bqs[0].getUrl());
			Log.d(TAG, "doInBackground: Url is : " + url);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setChunkedStreamingMode(0);

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(QUERY_ACTION, bqs[0].getAction()));
			params.add(new BasicNameValuePair(QUERY_PARAMS, bqs[0].getParams()));
			params.add(new BasicNameValuePair(QUERY_COOKIE, bqs[0].getCookie()));

			OutputStream os = urlConnection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					os, "UTF-8"));
			Log.d(TAG, "doInBackground parameters: " + getQuery(params));
			writer.write(getQuery(params));
			writer.flush();
			writer.close();
			os.close();
			in = urlConnection.getInputStream();
			result = readStream(in);
			in.close();
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			urlConnection.disconnect();
		}
		return result;
	}
	
	private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
	{
	    StringBuilder result = new StringBuilder();
	    boolean first = true;

	    for (NameValuePair pair : params)
	    {
	        if (first) {
	            first = false;
	        }
	        else {	
	            result.append("&");
	        }
	        if(!TextUtils.isEmpty(pair.getValue())) {
	        	result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
	        	result.append("=");
	        	result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
	        }
	    }

	    return result.toString();
	}
	
	private String readStream(InputStream in) {
		String result = "";
		StringBuffer response = new StringBuffer(); 
		String line;
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(in));
		
		try {
			while ((line = rd.readLine()) != null) {
				response.append(line);
			}
		} catch (IOException ioe) {
			Log.e(TAG, "readStream: IOException while reading server content");
		}
		result = response.toString();
		Log.d(TAG, "readStream returned: " + result);
		return response.toString();
	}

}
