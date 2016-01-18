package placard.fr.eu.org.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xylome on 18/01/16.
 */
public class AutoCompleteAdapter extends ArrayAdapter {

    private final ArrayList mList;




    public AutoCompleteAdapter(Context context, int resource, ArrayList list) {
        super(context, resource, list);
        mList = list;
    }





}
