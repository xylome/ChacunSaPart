package placard.fr.eu.chacunsapart.ui;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import placard.fr.eu.chacunsapart.chacunsapart.backend.beans.BackendObject;
import placard.fr.eu.chacunsapart.chacunsapart.backend.beans.Balance;
import placard.fr.eu.chacunsapart.chacunsapart.backend.beans.Group;
import placard.fr.eu.chacunsapart.chacunsapart.backend.exceptions.BackendException;
import placard.fr.eu.chacunsapart.chacunsapart.backend.lib.Backend;
import placard.fr.eu.chacunsapart.ui.listener.BalanceFragmentListener;
import placard.fr.eu.chacunsapart.chacunsapart.backend.beans.GroupBalances;
import placard.fr.eu.chacunsapart.chacunsapart.backend.listeners.BackendListener;

/**
 *
 */
public class BalanceFragment extends ListFragment implements BackendListener {

    private static final String TAG = BalanceFragment.class.getSimpleName() ;

    private static final String GROUP = "group";

    private Group mGroup = null;

    private Backend mBackend;

    private BalanceFragmentListener mListener;

    public static BalanceFragment newInstance(Group group) {
        BalanceFragment fragment = new BalanceFragment();
        Bundle args = new Bundle();
        args.putParcelable(GROUP, group);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BalanceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            mGroup = getArguments().getParcelable(GROUP);
        }

        mBackend = Backend.getInstance(getActivity().getApplicationContext());

        mBackend.getBalances(this, mGroup.getId());


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (BalanceFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onBalanceFragmentInterraction("bar bar");
        }
    }


    public void onBackendResponse(BackendObject bo) {
        GroupBalances gb = (GroupBalances) bo;
        Log.d(TAG, "Group balances received: " + gb);
        setListAdapter(new ArrayAdapter<Balance>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, gb.getBalances()));
    }

    public void onBackendError(BackendException be) {

    }

}
