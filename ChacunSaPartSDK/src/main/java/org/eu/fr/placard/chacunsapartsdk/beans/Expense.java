package org.eu.fr.placard.chacunsapartsdk.beans;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;
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
}
