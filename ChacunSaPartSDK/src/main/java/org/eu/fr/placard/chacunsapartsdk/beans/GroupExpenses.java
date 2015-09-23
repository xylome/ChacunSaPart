package org.eu.fr.placard.chacunsapartsdk.beans;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;
/**
 * Created by xylome on 22/09/15.
 */
public class GroupExpenses extends BackendObject {
    @SerializedName("data")
    private ArrayList<Expense> mExpenses = new ArrayList<Expense>();


    public ArrayList<Expense> getExpenses() {
        return mExpenses;
    }

    @Override
    public String toString() {
        return "Nb expenses: " + mExpenses.size();
    }
}
