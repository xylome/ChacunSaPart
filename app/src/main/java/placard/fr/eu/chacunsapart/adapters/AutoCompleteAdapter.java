package placard.fr.eu.chacunsapart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import placard.fr.eu.chacunsapart.backend.beans.Friend;

import placard.fr.eu.org.chacunsapart.R;
/**
 * Created by xylome on 18/01/16.
 * http://stackoverflow.com/questions/8784249/android-autocompletetextview-with-custom-adapter-filtering-not-working
 */
public class AutoCompleteAdapter extends ArrayAdapter<Friend> {
    private LayoutInflater layoutInflater;
    List<Friend> mFriends;
    private int mViewResourceId;


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
                for (Friend friend : mFriends) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (friend.getActorNick().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(friend);
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
                addAll(mFriends);
            }
            notifyDataSetChanged();
        }
    };

    public AutoCompleteAdapter(Context context, int textViewResourceId, List<Friend> friends, List<Friend> participating) {
        super(context, textViewResourceId, friends);
        // copy all the friends into a master list
        mFriends = new ArrayList<>(friends.size());
        mFriends.addAll(friends);
        mViewResourceId = textViewResourceId;
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(mViewResourceId, null);
        }

        Friend friend = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.auto_complete_nick);
        name.setText(friend.getActorNick());

        return view;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }
}