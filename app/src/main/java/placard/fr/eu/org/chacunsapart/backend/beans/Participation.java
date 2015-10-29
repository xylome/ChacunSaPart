package placard.fr.eu.org.chacunsapart.backend.beans;


import android.provider.Telephony;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xylome on 22/09/15.
 */
public class Participation {
    private static final String TAG = Participation.class.getSimpleName();

    @SerializedName("participation_id")
    private int mId;

    @SerializedName("guest_nick")
    private String mGuestNick;

    @SerializedName("guest_id")
    private int mGuestId;

    @SerializedName("parts")
    private int mParts;


    public int getParts() {
        return mParts;
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
        mParts --;
    }
}

