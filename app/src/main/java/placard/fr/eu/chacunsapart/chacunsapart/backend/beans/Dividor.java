package placard.fr.eu.chacunsapart.chacunsapart.backend.beans;

/**
 * Created by xylome on 21/10/2015.
 */
public class Dividor {

    private int mVal;

    public Dividor(int val) {
        mVal = val;
    }

    public String toString() {
        if (mVal == 1) {
            return mVal + "";
        } else {
            return "1/" + mVal;
        }
    }

    public int getValue() {
        return mVal;
    }

}
