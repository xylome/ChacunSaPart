package placard.fr.eu.org.chacunsapart.backend.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by xylome on 22/09/15.
 */
public class GroupExpenses extends BackendObject {
    @SerializedName("data")
    private ArrayList<Expense> mExpenses = new ArrayList<Expense>();


    public ArrayList<Expense> getExpenses() {
        ArrayList<Expense> expenses = new ArrayList<Expense>();

        for (Expense e: mExpenses) {
            if (!e.isPayback()) {
                expenses.add(e);
            }
        }
        return expenses;
    }

    public ArrayList<Expense> getPaybacks() {
        ArrayList<Expense> expenses = new ArrayList<Expense>();

        for (Expense e: mExpenses) {
            if (e.isPayback()) {
                expenses.add(e);
            }
        }
        return expenses;
    }

    @Override
    public String toString() {
        return "Nb expenses: " + mExpenses.size();
    }
}
