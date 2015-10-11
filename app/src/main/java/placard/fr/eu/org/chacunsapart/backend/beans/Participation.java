package placard.fr.eu.org.chacunsapart.backend.beans;


import com.google.gson.annotations.SerializedName;

/**
 * Created by xylome on 22/09/15.
 */
public class Participation {
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
}
