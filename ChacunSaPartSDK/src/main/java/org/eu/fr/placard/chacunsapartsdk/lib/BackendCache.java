package org.eu.fr.placard.chacunsapartsdk.lib;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by xylome on 26/09/15.
 */
public class BackendCache {
    private static final String TAG = BackendCache.class.getSimpleName();

    private Context mContext;

    private File mBasePath;

    public BackendCache(Context ctx) {
        mContext = ctx;
        mBasePath = mContext.getFilesDir();
    }

    public String readFromCache(String what, int group_id) {
        String result = "";
        File src = new File(mBasePath, group_id + "/" + what);
        FileInputStream in;

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

    public boolean writeToCache(String content, String what, int group_id) {
        Log.d(TAG, "writing to cache: what: " + what + " group_id:" + group_id);
        File dir = new File(mBasePath, group_id + "");
        File dest = new File(mBasePath, group_id + "/" + what);
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

    public long getAge(String what, int group_id) {
        File dest = new File(mBasePath, group_id + "/" + what);

        if (!dest.isFile()) {
            return 0;
        }

        return dest.lastModified();
    }

    public boolean cacheExists(String what, int group_id) {
        File dest = new File(mBasePath, group_id + "/" + what);
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
}
