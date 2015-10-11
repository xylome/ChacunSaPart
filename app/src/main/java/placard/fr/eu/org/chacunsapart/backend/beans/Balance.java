package placard.fr.eu.org.chacunsapart.backend.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xylome on 26/09/15.
 */
public class Balance {
    @SerializedName("actor_id")
    private int mActorID ;

    @SerializedName("actor_nick")
    private String mActorNick;

    @SerializedName("balance")
    private float mBalance;

    public Balance(int mActorID, String mActorString, float mBalance) {
        this.mActorID = mActorID;
        this.mActorNick = mActorString;
        this.mBalance = mBalance;
    }

    public int getActorID() {
        return mActorID;
    }

    public void setActorID(int mActorID) {
        this.mActorID = mActorID;
    }

    public String getActorString() {
        return mActorNick;
    }

    public void setActorString(String mActorString) {
        this.mActorNick = mActorString;
    }

    public float getBalance() {
        return mBalance;
    }

    public void setBalance(float mBalance) {
        this.mBalance = mBalance;
    }

    public String toString() {
        return mActorNick + " " + mBalance + " €";
    }
}
