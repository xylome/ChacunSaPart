package placard.fr.eu.chacunsapart.chacunsapart.backend.beans;

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

    @SerializedName("payer_id")
    private int mPayerId;

    @SerializedName("payer_nick")
    private String mPayerNick;

    @SerializedName("participations")
    private ArrayList<Participation> mParticipations = new ArrayList<Participation>();

    public Expense() {

    }

    public Expense(String name, float amount, int payer_id, ArrayList<Participation> participations) {
        mName = name;
        mAmount = amount;
        mPayerId = payer_id;
        mParticipations = participations;
    }

    protected Expense(Expense e) {
        mName = e.getName();
        mAmount = e.getAmount();
        mPayerId = e.getPayerId();
        mParticipations = e.getParticipations();
    }

    protected void unsetParticipations() {
        mParticipations.clear();
    }

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

    public int getPayerId() {
        return mPayerId;
    }

    public String getPayerNick() {
        return mPayerNick;
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
