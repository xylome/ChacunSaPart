package placard.fr.eu.org.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import placard.fr.eu.org.adapters.ExpenseAdapter;
import placard.fr.eu.org.chacunsapart.backend.beans.BackendObject;
import placard.fr.eu.org.chacunsapart.backend.beans.Expense;
import placard.fr.eu.org.chacunsapart.backend.beans.Group;
import placard.fr.eu.org.chacunsapart.backend.beans.GroupExpenses;
import placard.fr.eu.org.chacunsapart.backend.exceptions.BackendException;
import placard.fr.eu.org.chacunsapart.backend.lib.Backend;
import placard.fr.eu.org.chacunsapart.backend.listeners.BackendListener;
import placard.fr.eu.org.chacunsaparttesteur.R;
import placard.fr.eu.org.ui.listener.ExpenseFragmentListener;

/**
 *
 */
public class ExpenseFragment extends Fragment implements BackendListener, View.OnClickListener {

    private static final String TAG = ExpenseFragment.class.getSimpleName() ;

    private static final String GROUP = "group";

    private Group mGroup = null;

    private Backend mBackend;

    private ExpenseFragmentListener mListener;

    private FloatingActionButton mFab;

    private ListView mExpensesLV;

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

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_expense_list, container, false);

        mFab = (FloatingActionButton) v.findViewById(R.id.expense_fab);
        //mFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.csp_orange)));
        mFab.setOnClickListener(this);
        mExpensesLV = (ListView) v.findViewById(R.id.expense_lv);

        mBackend = Backend.getInstance(getActivity().getApplicationContext());



        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: requesting getExpense");
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

//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//
//        if (null != mListener) {
//            // Notify the active callbacks interface (the activity, if the
//            // fragment is attached to one) that an item has been selected.
//            mListener.onExpenseFragmentInterraction("bar bar");
//        }
//    }


    public void onBackendResponse(BackendObject bo) {
        GroupExpenses ge = (GroupExpenses) bo;
        Log.d(TAG, "Group Expenses received: " + ge);
        //setListAdapter(new ArrayAdapter<Expense>(getActivity(),
        //        android.R.layout.simple_list_item_1, android.R.id.text1, ge.getExpenses()));

        mGroup.setGroupExpenses(ge);

        if (ge.getExpenses() != null) {
            mExpensesLV.setAdapter(new ExpenseAdapter(getActivity().getApplicationContext(), mGroup));
            mExpensesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   startActivity(EditExpenseActivity.getIntent(getActivity().getApplicationContext(), ((Expense) adapterView.getItemAtPosition(i)).getId(), mGroup));
                }
            });
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Expenses null", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackendError(BackendException be) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.expense_fab) {
            startActivity(EditExpenseActivity.getNewExpenseIntent(getActivity().getApplicationContext(), mGroup));
        }
    }
}
