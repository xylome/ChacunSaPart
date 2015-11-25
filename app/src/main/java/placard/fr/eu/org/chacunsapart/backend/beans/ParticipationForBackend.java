package placard.fr.eu.org.chacunsapart.backend.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xylome on 25/11/2015.
 */
public class ParticipationForBackend extends Participation {

    @SerializedName("guest_actor_id")
    private int mGuestActorId;

    @SerializedName("nb_parts")
    private int mNbParts;

    public ParticipationForBackend(Participation p) {
        super(p);
        mGuestActorId = getGuestId();
        mNbParts = getParts();
    }
}
