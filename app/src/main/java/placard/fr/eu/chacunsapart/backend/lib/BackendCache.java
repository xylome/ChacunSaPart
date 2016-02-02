package placard.fr.eu.chacunsapart.backend.lib;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import placard.fr.eu.chacunsapart.backend.conf.BackendConf;

/**
 * Created by xylome on 26/09/15.
 */
public class BackendCache implements BackendConf {
    private static final String TAG = BackendCache.class.getSimpleName();

    private Context mContext;

    private File mBasePath;

    private String mWhat;

    public BackendCache(Context ctx) {
        mContext = ctx;
        mBasePath = mContext.getFilesDir();
    }

    public void setWhat(String backendVerb) {
        Log.d(TAG, "Setting what for verb: " + backendVerb);
        if (backendVerb.equals(ACTION_MYGROUPS)) {
            mWhat = CACHE_MYGROUPS;
        }

        if (backendVerb.equals(ACTION_GET_EXPENSES)) {
            mWhat = CACHE_EXPENSES;
        }

        if (backendVerb.equals(ACTION_GET_BALANCES)) {
            mWhat = CACHE_BALANCES;
        }

        if (backendVerb.equals(ACTION_GET_MY_FRIENDS)) {
            mWhat = CACHE_FRIENDS;
        }
        Log.d(TAG, "What is now: " + mWhat);
    }

    public String readFromCache(int group_id) {
        String result = "";

        File src = new File(mBasePath, group_id + "/" + mWhat);
        FileInputStream in;

        Log.d(TAG, "Reading from: " + src.getAbsolutePath());

        if (!src.isFile()) {
            return "";
        }

        try {
            in = new FileInputStream(src);
            result = convertStreamToString(in);
            in.close();
        } catch (Exception e) {
            Log.e(TAG, "Error while reading file: " + src.getAbsolutePath() + ". " + e.getMessage());
        }
        return result;
    }

    public boolean writeToCache(String content, int group_id) {
        Log.d(TAG, "writing to cache: what: " + mWhat + " group_id:" + group_id);
        File dir = new File(mBasePath, group_id + "");
        File dest = new File(mBasePath, group_id + "/" + mWhat);

        Log.d(TAG, "writing to file: " + dest.getAbsolutePath());

        FileOutputStream outputStream;

        if (!dir.isDirectory()) {
            dir.mkdirs();
        }

        try {
            outputStream = new FileOutputStream(dest);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            Log.e(TAG, "Cannot write to file: " + dest.getAbsolutePath() + ". " + e.getMessage());
        }
        return true;
    }

    public long getAgeFromNow(int group_id) {
        long now = System.currentTimeMillis();
        File dest = new File(mBasePath, group_id + "/" + mWhat);

        if (!dest.isFile()) {
            return 0;
        }
        return now - dest.lastModified();
    }

    public boolean cacheExists(int group_id) {
        File dest = new File(mBasePath, group_id + "/" + mWhat);
        return dest.exists();
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public void invalidate(String verb, int group_id) {

        setWhat(verb);
        if (cacheExists(group_id)) {
            File dest = new File(mBasePath, group_id + "/" + mWhat);
            dest.delete();
            Log.d(TAG, "deleting file: " + dest.getName());
        }
    }
}
