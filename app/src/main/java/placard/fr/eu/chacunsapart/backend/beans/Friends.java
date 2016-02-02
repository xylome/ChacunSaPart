package placard.fr.eu.chacunsapart.backend.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by xylome on 28/10/2015.
 */
public class Friends extends BackendObject{
    @SerializedName("data")
    private ArrayList<Friend> mFriends;



    public int getCount() {
        return mFriends.size();
    }


    public Friend getAtPos(int index) {
        return mFriends.get(index);
    }

    public ArrayList<Friend> getFriends() {
        return mFriends;
    }

    public int findPosbyNick(String nick) {
        int i = 0;
        for (Friend friend: mFriends) {
            if(friend.getActorNick().equals(nick)) {
                return i;
            }
            i++;
        }

        return -1;
    }
 }
