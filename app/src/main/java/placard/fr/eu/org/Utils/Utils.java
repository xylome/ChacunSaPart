package placard.fr.eu.org.Utils;

import android.text.TextUtils;

import java.util.ArrayList;

import placard.fr.eu.org.chacunsapart.backend.beans.Friend;
import placard.fr.eu.org.chacunsapart.backend.beans.Participation;

/**
 * Created by xylome on 25/01/16.
 */
public class Utils {

    public static ArrayList<Friend> extractFriendsFromParticipation(ArrayList<Participation> participations) {
        ArrayList<Friend> friends = new ArrayList<>();
        for (Participation current : participations) {
            Friend f = new Friend(current.getGuestNick(), current.getGuestId());
            friends.add(f);
        }
        return friends;
    }

    public static Friend resolveFriend(String nickname, ArrayList<Friend> friends) {
        Friend result = null;

        if (TextUtils.isEmpty(nickname)) {
            return null;
        }

        for (Friend current : friends) {
            if (current.getActorNick().toLowerCase().equals(nickname.toLowerCase())) {
                result = current;
                break;
            }
        }

        if (result == null) {
            result = new Friend(nickname);
        }

        return result;
    }
}
