package placard.fr.eu.org.chacunsapart.backend.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xylome on 25/11/2015.
 */
public class ExpenseForBackend extends Expense {

    @SerializedName("group_id")
    private int mGroupId;

    @SerializedName("payer_actor_id")
    private int mPayerActorId;

    @SerializedName("guests")
    ArrayList<ParticipationForBackend> mGuests = new ArrayList<>();

    public ExpenseForBackend(Expense e, int group_id) {
        super(e);
        mGroupId = group_id;
        mPayerActorId = e.getPayerId();
        mGuests = mapParticipations();
        unsetParticipations();
    }

    private ArrayList<ParticipationForBackend> mapParticipations() {
        ArrayList<ParticipationForBackend> result = new ArrayList<>();

        for (Participation currPart : getParticipations()) {
            result.add(new ParticipationForBackend(currPart));
        }

        return result;
    }


}
