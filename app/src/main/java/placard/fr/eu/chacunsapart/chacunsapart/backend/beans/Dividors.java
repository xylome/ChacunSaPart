package placard.fr.eu.chacunsapart.chacunsapart.backend.beans;

import java.util.ArrayList;

/**
 * Created by xylome on 21/10/2015.
 */
public class Dividors {

    private static final String TAG = Dividors.class.getSimpleName();
    private ArrayList<Dividor> mDiv = new ArrayList<Dividor>();

    public Dividors(int count) {
        for (int i = 0; i < count; i++) {
            mDiv.add(new Dividor(i + 1));
        }
    }

    public int getCount() {
        return mDiv.size();
    }

    public Dividor getAtPos(int pos) {
        return mDiv.get(pos);
    }

    public String display(int pos) {
        return mDiv.get(pos).toString();
    }
}
