package org.eu.fr.placard.chacunsapartsdk.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by xylome on 22/09/15.
 */
public class GroupBalances extends BackendObject {
    @SerializedName("balances")
    private ArrayList<Balance> mBalances = new ArrayList<Balance>();
    @SerializedName("suggestions")
    private ArrayList<Suggestion> mSuggestions = new ArrayList<Suggestion>();

    public ArrayList<Balance> getBalances() {
        return mBalances;
    }

    public ArrayList<Suggestion> getSuggestions() {
        return mSuggestions;
    }

    public String toString() {
        return "balances: " + mBalances.size() + " suggestions: " + mSuggestions.size();
    }
}
