package placard.fr.eu.org.chacunsapart.backend.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xylome on 28/10/2015.
 */
public class Friend {
    @SerializedName("actor_id")
    private int mActorId;

    @SerializedName("actor_nick")
    private String mActorNick;

    @SerializedName("acct_id")
    private int mAccountId;

    public Friend(String nick) {
        mActorNick = nick;
    }

    public int getActorId() {
        return mActorId;
    }

    public String getActorNick() {
        return mActorNick;
    }

    public int getmAccountId() {
        return mAccountId;
    }

    public String toString() {
        return getActorNick();
    }
}
