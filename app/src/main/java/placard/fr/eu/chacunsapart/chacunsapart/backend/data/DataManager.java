package placard.fr.eu.chacunsapart.chacunsapart.backend.data;



import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class DataManager {
	
		private static final String TAG = DataManager.class.getSimpleName();

		private static final String ACCOUNT_ID = "account_id";

		private static final String COOKIE = "cookie";

		private static final String EMAIL = "email";

		private static final String ACTOR_ID = "actor_id";

		private static final String NICK = "nick";

		private static DataManager mInstance;

		private SharedPreferences mPref;

		private DataManager(Context c) {
			mPref = PreferenceManager
					.getDefaultSharedPreferences(c);
		}

		public static DataManager getInstance(Context context) {
			if (mInstance == null) {
				mInstance = new DataManager(context);
			}
			return mInstance;
		}

		private void writeString(String key, String value){
		     Editor e = mPref.edit();
		     e.putString(key, value);
		     e.commit();
		}
		
		private void writeInt(String key, int value) {
			Editor e = mPref.edit();
			e.putInt(key, value);
			e.commit();
		}
		
		public String getAccountId() {
			return mPref.getString(ACCOUNT_ID, null);
		}
		
		public void writeAccountId(String id) {
			writeString(ACCOUNT_ID, id);
		}
		
		public String getNick() {
			return mPref.getString(NICK, null);
		}
		
		public void writeNick(String id) {
			writeString(NICK, id);
		}
		
		public int getActorId() {
			return mPref.getInt(ACTOR_ID, -1);
		}
		
		public void writeActorId(int id) {
			writeInt(ACTOR_ID, id);
		}
		
		public String getEmail() {
			return mPref.getString(EMAIL, null);
		}
		
		public void writeEmail(String id) {
			writeString(EMAIL, id);
		}
		
		/**
		 * Gets the cookie or rull
		 * @return {@link String} cookie.
		 */
		public String getCookie() {
			return mPref.getString(COOKIE, null);
		}
		
		public void writeCookie(String id) {
			writeString(COOKIE, id);
		}
		
}
