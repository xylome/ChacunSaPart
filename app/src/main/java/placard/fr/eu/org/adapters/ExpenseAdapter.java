package placard.fr.eu.org.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import placard.fr.eu.org.chacunsapart.backend.beans.Expense;
import placard.fr.eu.org.chacunsapart.backend.beans.Group;
import placard.fr.eu.org.chacunsaparttesteur.R;

/**
 * Created by xylome on 24/10/2015.
 */
public class ExpenseAdapter extends BaseAdapter {

    private static final String TAG = ExpenseAdapter.class.getSimpleName();
    private Context mContext;
    private Group mGroup;
    private ArrayList<Expense> mExpenses;

    public ExpenseAdapter(Context c, Group group) {
        mGroup = group;
        mExpenses = group.getGroupExpenses().getExpenses();
        mContext = c;
    }

    @Override
    public int getCount() {
        return mExpenses.size();
    }

    @Override
    public Object getItem(int i) {
        return mExpenses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TextView name = null;
        TextView amount = null;
        TextView whole_parts = null;
        TextView decim_parts = null;


        if (view == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (LinearLayout) li.inflate(R.layout.item_expense, null);
            LinearLayout parts_lv = (LinearLayout) view.findViewById(R.id.item_expense_parts);
            name = (TextView) view.findViewById(R.id.item_expense_name);
            amount = (TextView) view.findViewById(R.id.item_expense_amount);
            whole_parts = (TextView) view.findViewById(R.id.item_expense_whole_parts);
            decim_parts = (TextView) view.findViewById(R.id.item_expense_decim_parts);
        }

        Expense e = (Expense) getItem(i);
        LinearLayout parts_lv = (LinearLayout) view.findViewById(R.id.item_expense_parts);

        name.setText(e.getName());
        amount.setText(e.getAmount()+" €");
        whole_parts.setText(e.displayWholeParts(mGroup.getFraction()));
        decim_parts.setText(Html.fromHtml(e.displayDecimParts(mGroup.getFraction(), true)));

        if (e.displayDecimParts(mGroup.getFraction(),false).equals("")) {
            decim_parts.setVisibility(View.GONE);
        } else {
            decim_parts.setVisibility(View.VISIBLE);
        }

        if(e.getNbParts() == 0) {
            parts_lv.setBackground(mContext.getResources().getDrawable(R.drawable.nb_parts_equals_0));
        } else {
            parts_lv.setBackground(mContext.getResources().getDrawable(R.drawable.nb_parts_supp_0));
        }

        return view;
    }
}