package placard.fr.eu.chacunsapart.backend.lib;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import placard.fr.eu.chacunsapart.backend.beans.Expense;
import placard.fr.eu.chacunsapart.backend.beans.ExpenseForBackend;
import placard.fr.eu.chacunsapart.backend.beans.Group;
import placard.fr.eu.chacunsapart.backend.beans.Participation;
import placard.fr.eu.chacunsapart.backend.conf.BackendConf;

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


	public static String buildGetMyFriendsParams(String account_id) {
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


	public static String buildCreateGroupParams(Group group) {
        String result = null;

        JSONObject obj = new JSONObject();
        try {
            obj.put(FIELD_GROUP_NAME, group.getName());
            obj.put(FIELD_GROUP_FRACTION, group.getFraction());
            obj.put(FIELD_CREATOR_ACTOR_ID, group.getCreatorId());

            result = obj.toString();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

	public static String buildGetExpensesParams(int group_id) {
		String result = null;

        JSONObject obj = new JSONObject();
        try {
            obj.put(FIELD_GROUP_ID, group_id);
            result = obj.toString();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
	}

	public static String buildGetBalancesParams(int group_id) {
		String result = null;

		JSONObject obj = new JSONObject();
		try {
			obj.put(FIELD_GROUP_ID, group_id);
			result = obj.toString();
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}

	public static String buildUpdateParticipationParams(int expense_id, Participation particpation) {
        String result = null;

        JSONObject obj = new JSONObject();
        try {
            obj.put(FIELD_EXPENSE_ID, expense_id);
            obj.put(FIELD_GUEST_ACTOR_ID, particpation.getGuestId());
			if (particpation.getGuestId() == 0) {
              obj.put(FIELD_NEW_GUEST_NICK, particpation.getGuestNick());
            }
            obj.put(FIELD_PARTS, particpation.getParts());
            obj.put(QUERY_ABSOLUTE_PARTS, 1);
            result = obj.toString();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return result;

    }

	public static String buildDeleteParticipationParams(int participation_id) {
		String result = null;

        JSONObject obj = new JSONObject();

        try {
            obj.put(FIELD_PARTICIPATION_ID, participation_id);
            result = obj.toString();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return result;
	}

	public static String buildCreateExpenseParams(int group_id, Expense e) {
		String result = null;
		ExpenseForBackend efb = new ExpenseForBackend(e, group_id);
		Gson gson = new Gson();
		result = gson.toJson(efb);
		return result;
	}
}
