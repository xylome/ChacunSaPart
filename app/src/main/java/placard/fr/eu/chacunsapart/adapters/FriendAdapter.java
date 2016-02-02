package placard.fr.eu.chacunsapart.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import placard.fr.eu.chacunsapart.backend.beans.Friends;
import placard.fr.eu.org.chacunsapart.R;

/**
 * Created by xylome on 21/10/2015.
 */
public class FriendAdapter extends ArrayAdapter {

    private Context mContext;

    private Friends mFriends;


    public FriendAdapter(Context context, int resource, Friends friends) {
        super(context, resource);
        mFriends = friends;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mFriends.getCount();
    }

    @Override
    public Object getItem(int i) {
        return mFriends.getAtPos(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getPosition(Object o) {
        return mFriends.findPosbyNick((String) o);
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        if(row == null)
        {
            //inflate your customlayout for the textview
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_friends_item, parent, false);
        }
        //put the data in it
        TextView text1 = (TextView) row.findViewById(R.id.spinner_friends_item_tv);
        //text1.setTextColor(Color.WHITE);
        text1.setText(mFriends.getAtPos(position).getActorNick());
        return row;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
