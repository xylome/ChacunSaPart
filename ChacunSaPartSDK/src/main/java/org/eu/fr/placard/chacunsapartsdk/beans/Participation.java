package org.eu.fr.placard.chacunsapartsdk.beans;


import com.google.gson.annotations.SerializedName;

/**
 * Created by xylome on 22/09/15.
 */
public class Participation {
    @SerializedName("participation_id")
    private int mId;

    private String mGuestNick;

    private int mGuestId;

    private int mParts;

}
