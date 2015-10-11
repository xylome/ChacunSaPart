package placard.fr.eu.org.ui;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import placard.fr.eu.org.chacunsapart.backend.beans.BackendObject;
import placard.fr.eu.org.chacunsapart.backend.beans.Expense;
import placard.fr.eu.org.chacunsapart.backend.beans.Group;
import placard.fr.eu.org.chacunsapart.backend.beans.GroupExpenses;
import placard.fr.eu.org.chacunsapart.backend.exceptions.BackendException;
import placard.fr.eu.org.chacunsapart.backend.lib.Backend;
import placard.fr.eu.org.chacunsapart.backend.listeners.BackendListener;

import placard.fr.eu.org.ui.listener.ExpenseFragmentListener;

/**
 *
 */
public class ExpenseFragment extends ListFragment implements BackendListener {

    private static final String TAG = ExpenseFragment.class.getSimpleName() ;

    private static final String GROUP = "group";

    private Group mGroup = null;

    private Backend mBackend;

    private ExpenseFragmentListener mListener;

    public static ExpenseFragment newInstance(Group group) {
        ExpenseFragment fragment = new ExpenseFragment();
        Bundle args = new Bundle();
        args.putParcelable(GROUP, group);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExpenseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mGroup = getArguments().getParcelable(GROUP);
        }



        mBackend = Backend.getInstance(getActivity().getApplicationContext());

        mBackend.getExpenses(this, mGroup.getId());

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ExpenseFragmentListener) activity;
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
            mListener.onExpenseFragmentInterraction("bar bar");
        }
    }


    public void onBackendResponse(BackendObject bo) {
        GroupExpenses ge = (GroupExpenses) bo;
        Log.d(TAG, "Group Expenses received: " + ge);
        setListAdapter(new ArrayAdapter<Expense>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, ge.getExpenses()));
    }

    public void onBackendError(BackendException be) {

    }

}
