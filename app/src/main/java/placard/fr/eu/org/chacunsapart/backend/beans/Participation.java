package placard.fr.eu.org.chacunsapart.backend.beans;


import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xylome on 22/09/15.
 */
public class Participation extends BackendObject {
    private static final String TAG = Participation.class.getSimpleName();

    private  static final int NEW_GUEST_ID = 0;

    @SerializedName("participation_id")
    private int mId;

    @SerializedName("guest_nick")
    private String mGuestNick;

    @SerializedName("guest_id")
    private int mGuestId;

    @SerializedName("parts")
    private int mParts;

    public Participation() {

    }

    protected Participation(Participation p) {
        mId = p.getId();
        mGuestNick = p.getGuestNick();
        mGuestId = p.getGuestId();
        mParts = p.getParts();
    }

    public Participation(int guest_id, int parts, String nick) {
        mGuestId = guest_id;
        mParts = parts;

        if (!TextUtils.isEmpty(nick)) {
            mGuestNick = nick;
        }

        if (guest_id == NEW_GUEST_ID) {
            mGuestNick = nick;
        }
    }

    public int getId() {
        return mId;
    }

    public int getParts() {
        return mParts;
    }

    public int getGuestId() {
        return mGuestId;
    }

    public String getNick() {
        return mGuestNick;
    }

    public String getGuestNick() {
        return mGuestNick;
    }

    public String displayParts(int groupFraction, boolean htmlMode) {
        String result = "";
        int tmpFraction = groupFraction;
        int tmpParts = getParts();

        int entirePart = tmpParts / tmpFraction;
        int rest = tmpParts - (entirePart * tmpFraction);

        if (entirePart == 0 && rest == 0) {
            result = "0";
        }

        if (entirePart > 0) {
            result = entirePart + " ";
        }

        if (rest == 2 && tmpFraction == 4) {
            rest = 1;
            tmpFraction = 2;
        }

        if (rest > 0) {
            if (htmlMode) {
                result += "<sup>" + rest + "</sup>/<sub>" + tmpFraction + "</sub>";
            } else {
                result += rest + "/" + tmpFraction;
            }
        }
        return result.trim();
    }

    public String displayWholeParts(int groupFraction) {
        String result = "";
        int tmpFraction = groupFraction;
        int tmpParts = getParts();

        int entirePart = tmpParts / tmpFraction;

        result += entirePart;

        return result.trim();
    }

    public String displayDecimParts(int groupFraction, boolean htmlMode) {
        String result = "";
        int tmpFraction = groupFraction;
        int tmpParts = getParts();

        int entirePart = tmpParts / tmpFraction;
        int rest = tmpParts - (entirePart * tmpFraction);

        if (entirePart == 0 && rest == 0) {
            result = "";
        }

        if (rest == 2 && tmpFraction == 4) {
            rest = 1;
            tmpFraction = 2;
        }

        if (rest > 0) {
            if (htmlMode) {
                result += "<sup>" + rest + "</sup>/<sub>" + tmpFraction + "</sub>";
            } else {
                result += rest + "/" + tmpFraction;
            }
        }

        return result.trim();
    }

    public void addPart() {
        Log.d(TAG, "Adding a part");
        mParts ++;
    }

    public void subPart() {
        if (mParts > 0) {
            mParts--;
        }
    }
}

