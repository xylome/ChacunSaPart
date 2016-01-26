package placard.fr.eu.org.chacunsapart.backend.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xylome on 28/10/2015.
 */
public class Friend implements Parcelable {
    @SerializedName("actor_id")
    private int mActorId;

    @SerializedName("actor_nick")
    private String mActorNick;

    @SerializedName("acct_id")
    private int mAccountId;

    public Friend(String nick) {
        mActorNick = nick;
    }

    public Friend(String nick, int actorId) {
        mActorId = actorId;
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
        return getActorId() + ':' +getActorNick();
    }

    public Friend(Parcel in) {
        this.mActorId = in.readInt();
        this.mActorNick = in.readString();
        this.mAccountId= in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mActorId);
        parcel.writeString(mActorNick);
        parcel.writeInt(mAccountId);
        }

    public static final Creator CREATOR = new Creator() {
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };



}
