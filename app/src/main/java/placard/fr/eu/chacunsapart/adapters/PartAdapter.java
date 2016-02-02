package placard.fr.eu.chacunsapart.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import placard.fr.eu.chacunsapart.backend.beans.Dividors;
import placard.fr.eu.org.chacunsapart.R;

/**
 * Created by xylome on 21/10/2015.
 */
public class PartAdapter extends ArrayAdapter {

    private Context mContext;
    private Dividors mDividors = new Dividors(4);

    public PartAdapter(Context context, int resource) {
        super(context, resource);
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDividors.getCount();
    }

    @Override
    public Object getItem(int i) {
        return mDividors.getAtPos(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        if(row == null)
        {
            //inflate your customlayout for the textview
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_part_item, parent, false);
        }
        //put the data in it
        TextView text1 = (TextView) row.findViewById(R.id.part_spinner_item_tv);
        //text1.setTextColor(Color.WHITE);
        text1.setText(mDividors.display(position));
        return row;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
