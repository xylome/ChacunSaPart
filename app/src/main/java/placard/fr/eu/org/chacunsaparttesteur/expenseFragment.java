package placard.fr.eu.org.chacunsaparttesteur;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.eu.fr.placard.chacunsapartsdk.beans.BackendObject;
import org.eu.fr.placard.chacunsapartsdk.exceptions.BackendException;
import org.eu.fr.placard.chacunsapartsdk.lib.Backend;
import org.eu.fr.placard.chacunsapartsdk.listeners.BackendListener;

import placard.fr.eu.org.chacunsaparttesteur.dummy.DummyContent;

/**
 *
 */
public class expenseFragment extends ListFragment implements BackendListener {

    private static final String GROUP_ID = "group_id";


    private int mGroupId = -1;

    private Backend mBackend;

    private OnFragmentInteractionListener mListener;


    public static expenseFragment newInstance(int group_id) {
        expenseFragment fragment = new expenseFragment();
        Bundle args = new Bundle();
        args.putInt(GROUP_ID, group_id);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public expenseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            mGroupId = getArguments().getInt(GROUP_ID);
        }

        mBackend = Backend.getInstance(getActivity().getApplicationContext());

        mBackend.getExpenses(this, mGroupId);

        // TODO: backend query here.
        setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS));
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
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
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }


    public void onBackendResponse(BackendObject bo) {

    }

    public void onBackendError(BackendException be) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
