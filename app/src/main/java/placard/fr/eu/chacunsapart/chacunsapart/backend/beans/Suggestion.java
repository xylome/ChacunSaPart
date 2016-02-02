package placard.fr.eu.chacunsapart.chacunsapart.backend.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xylome on 26/09/15.
 */
public class Suggestion {
    @SerializedName("from")
    private int mFrom;

    @SerializedName("from_nick")
    private String mFromNick;

    @SerializedName("to")
    private int mTo;

    @SerializedName("to_nick")
    private String mToNick;

    @SerializedName("amount")
    private float mAmount;

    public Suggestion(int mFrom, String mFromNick, int mTo, String mToNick, float mAmount) {
        this.mFrom = mFrom;
        this.mFromNick = mFromNick;
        this.mTo = mTo;
        this.mToNick = mToNick;
        this.mAmount = mAmount;
    }

    public int getmFrom() {
        return mFrom;
    }

    public void setmFrom(int mFrom) {
        this.mFrom = mFrom;
    }

    public String getmFromNick() {
        return mFromNick;
    }

    public void setmFromNick(String mFromNick) {
        this.mFromNick = mFromNick;
    }

    public int getmTo() {
        return mTo;
    }

    public void setmTo(int mTo) {
        this.mTo = mTo;
    }

    public String getmToNick() {
        return mToNick;
    }

    public void setmToNick(String mToNick) {
        this.mToNick = mToNick;
    }

    public float getmAmount() {
        return mAmount;
    }

    public void setmAmount(float mAmount) {
        this.mAmount = mAmount;
    }
}
