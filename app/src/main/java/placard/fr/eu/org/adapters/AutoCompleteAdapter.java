package placard.fr.eu.org.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import placard.fr.eu.org.chacunsapart.backend.beans.Friend;

import placard.fr.eu.org.chacunsaparttesteur.R;
/**
 * Created by xylome on 18/01/16.
 * http://stackoverflow.com/questions/8784249/android-autocompletetextview-with-custom-adapter-filtering-not-working
 */
public class AutoCompleteAdapter extends ArrayAdapter<Friend> {
    private LayoutInflater layoutInflater;
    List<Friend> mCustomers;

    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((Friend)resultValue).getActorNick();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                ArrayList<Friend> suggestions = new ArrayList<Friend>();
                for (Friend customer : mCustomers) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (customer.getActorNick().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(customer);
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<Friend>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(mCustomers);
            }
            notifyDataSetChanged();
        }
    };

    public AutoCompleteAdapter(Context context, int textViewResourceId, List<Friend> customers) {
        super(context, textViewResourceId, customers);
        // copy all the customers into a master list
        mCustomers = new ArrayList<Friend>(customers.size());
        mCustomers.addAll(customers);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.auto_complete, null);
        }

        Friend customer = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.auto_complete_nick);
        name.setText(customer.getActorNick());

        return view;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }
}