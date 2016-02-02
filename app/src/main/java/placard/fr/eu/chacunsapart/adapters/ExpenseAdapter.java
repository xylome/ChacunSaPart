package placard.fr.eu.chacunsapart.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import placard.fr.eu.chacunsapart.backend.beans.Group;
import placard.fr.eu.chacunsapart.backend.beans.Expense;
import placard.fr.eu.org.chacunsapart.R;

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
        if (view == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (LinearLayout) li.inflate(R.layout.item_expense, null);

            ViewHolder vh = new ViewHolder();
            vh.parts_lv = (LinearLayout) view.findViewById(R.id.item_expense_parts);
            vh.name = (TextView) view.findViewById(R.id.item_expense_name);
            vh.amount = (TextView) view.findViewById(R.id.item_expense_amount);
            vh.whole_parts = (TextView) view.findViewById(R.id.item_expense_whole_parts);
            vh.decim_parts = (TextView) view.findViewById(R.id.item_expense_decim_parts);

            view.setTag(vh);
        }

        ViewHolder vh = (ViewHolder) view.getTag();

        Expense e = (Expense) getItem(i);


        vh.name.setText(e.getName());
        vh.amount.setText(e.getAmount()+" €");
        vh.whole_parts.setText(e.displayWholeParts(mGroup.getFraction()));
        vh.decim_parts.setText(Html.fromHtml(e.displayDecimParts(mGroup.getFraction(), true)));

        if (e.displayDecimParts(mGroup.getFraction(),false).equals("")) {
            vh.decim_parts.setVisibility(View.GONE);
        } else {
            vh.decim_parts.setVisibility(View.VISIBLE);
        }

        if(e.getNbParts() == 0) {
            vh.parts_lv.setBackground(mContext.getResources().getDrawable(R.drawable.nb_parts_equals_0));
        } else {
            vh.parts_lv.setBackground(mContext.getResources().getDrawable(R.drawable.nb_parts_supp_0));
        }

        return view;
    }

    static class ViewHolder {
        public LinearLayout parts_lv;
        public TextView name;
        public TextView amount;
        public TextView whole_parts;
        public TextView decim_parts;
    }

}