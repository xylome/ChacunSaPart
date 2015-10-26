package placard.fr.eu.org.chacunsapart.backend.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by xylome on 22/09/15.
 */
public class Expense {
    @SerializedName("expense_id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("amount")
    private float mAmount;

    @SerializedName("is_payback")
    private int mIsPayback;

    @SerializedName("participations")
    private ArrayList<Participation> mParticipations = new ArrayList<Participation>();

    @Override
    public String toString() {
        return   mName + " " + mAmount + " €, pour " + getNbParts() + " parts.";
    }

    public int getNbParts() {
        int parts = 0;
        for (Participation p: mParticipations) {
            parts += p.getParts();
        }
        return parts;
    }

    public boolean isPayback() {
        return mIsPayback == 1;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public float getAmount() {
        return mAmount;
    }

    public ArrayList<Participation> getParticipations() {
        return mParticipations;
    }

    public String displayParts(int groupFraction, boolean htmlMode) {
        String result = "";
        int tmpFraction = groupFraction;
        int tmpParts = getNbParts();

        int entirePart = tmpParts / tmpFraction;
        int rest = tmpParts - (entirePart * tmpFraction);

        if (entirePart == 0 && rest == 0) {
            result = "0";
        }

        if (entirePart > 0) {
            result = entirePart + " ";
        }

        if (rest == 2 && tmpFraction == 4) {
            rest = 1;
            tmpFraction = 2;
        }

        if (rest > 0) {
            if (htmlMode) {
                result += "<sup>" + rest + "</sup>/<sub>" + tmpFraction + "</sub>";
            } else {
                result += rest + "/" + tmpFraction;
            }
        }
        return result.trim();
    }

    public String displayWholeParts(int groupFraction) {
        String result = "";
        int tmpFraction = groupFraction;
        int tmpParts = getNbParts();

        int entirePart = tmpParts / tmpFraction;

        result += entirePart;

        return result.trim();
    }

    public String displayDecimParts(int groupFraction, boolean htmlMode) {
        String result = "";
        int tmpFraction = groupFraction;
        int tmpParts = getNbParts();

        int entirePart = tmpParts / tmpFraction;
        int rest = tmpParts - (entirePart * tmpFraction);

        if (entirePart == 0 && rest == 0) {
            result = "";
        }

        if (rest == 2 && tmpFraction == 4) {
            rest = 1;
            tmpFraction = 2;
        }

        if (rest > 0) {
            if (htmlMode) {
                result += "<sup>" + rest + "</sup>/<sub>" + tmpFraction + "</sub>";
            } else {
                result += rest + "/" + tmpFraction;
            }
        }

        return result.trim();
    }
}
