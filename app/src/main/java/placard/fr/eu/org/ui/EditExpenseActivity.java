package placard.fr.eu.org.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

import placard.fr.eu.org.Utils.Utils;
import placard.fr.eu.org.adapters.FriendAdapter;
import placard.fr.eu.org.adapters.ParticipationAdapter;
import placard.fr.eu.org.chacunsapart.backend.beans.BackendObject;
import placard.fr.eu.org.chacunsapart.backend.beans.Expense;
import placard.fr.eu.org.chacunsapart.backend.beans.ExpenseResponse;
import placard.fr.eu.org.chacunsapart.backend.beans.Friend;
import placard.fr.eu.org.chacunsapart.backend.beans.Friends;
import placard.fr.eu.org.chacunsapart.backend.beans.Group;
import placard.fr.eu.org.chacunsapart.backend.beans.GroupExpenses;
import placard.fr.eu.org.chacunsapart.backend.beans.Participation;
import placard.fr.eu.org.chacunsapart.backend.exceptions.BackendException;
import placard.fr.eu.org.chacunsapart.backend.lib.Backend;
import placard.fr.eu.org.chacunsapart.backend.listeners.BackendListener;
import placard.fr.eu.org.chacunsapart.backend.listeners.BackendResponse;
import placard.fr.eu.org.chacunsaparttesteur.R;

public class EditExpenseActivity extends AppCompatActivity implements BackendListener, View.OnClickListener {

    private static final String EXPENSE_FIELD = "expense_field";

    private static final String GROUP_FIELD = "group_field";

    private static final String NEW_EXPENSE_FIELD = "new_expense_field";

    private static final String TAG = EditExpenseActivity.class.getSimpleName();
    private static final int ADD_A_FRIEND = 0x001 ;

    private int mExpenseId;

    private Group mGroup;

    private Expense mExpense;

    private boolean mNewGroup;

    private EditText mExpenseNameTV;

    private EditText mExpenseAmountTV;

    private Spinner mPayer;

    private Friends mFriends;

    private ListView mPartsLV;

    private boolean mEnableButtonOk = true;

    private android.support.v7.widget.Toolbar mToolbar;

    private ParticipationAdapter mParticipationAdapter;

    private int mParticipationsToProcess;
    private int mParticipationsProcessed;

    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);


        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);

        mFab = (FloatingActionButton) findViewById(R.id.edit_expense_fab);
        mFab.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        mNewGroup = extras.getBoolean(NEW_EXPENSE_FIELD);
        mGroup = extras.getParcelable(GROUP_FIELD);

        if (!mNewGroup) {
            mExpenseId = extras.getInt(EXPENSE_FIELD);
        }

        mExpenseNameTV = (EditText) findViewById(R.id.edit_expense_name_tv);

        mExpenseAmountTV = (EditText) findViewById(R.id.edit_expense_amount_tv);

        mPartsLV = (ListView) findViewById(R.id.edit_expense_participations_lv);

        mPayer = (Spinner) findViewById(R.id.edit_expense_payer_spinner);

        if (mNewGroup) {
            getSupportActionBar().setTitle(R.string.edit_expense_new_expense);
        } else {
            getSupportActionBar().setTitle(R.string.edit_expense);
        }

        if (!mNewGroup) {
            Backend.getInstance(getApplicationContext()).getExpenses(this, mGroup.getId());
        } else {
            Backend.getInstance(getApplicationContext()).getFriends(this);
        }
    }

    public static Intent getIntent(Context c, int expense_id, Group group) {
        Intent i = new Intent(c, EditExpenseActivity.class);
        i.putExtra(EXPENSE_FIELD, expense_id);
        i.putExtra(GROUP_FIELD, group);
        i.putExtra(NEW_EXPENSE_FIELD, false);
        return i;
    }

    public static Intent getNewExpenseIntent(Context c, Group group) {
        Intent i = new Intent(c, EditExpenseActivity.class);
        i.putExtra(GROUP_FIELD, group);
        i.putExtra(NEW_EXPENSE_FIELD, true);
        return i;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_expense, menu);
        menu.findItem(R.id.action_ok).setEnabled(mEnableButtonOk);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_ok) {
            Log.d(TAG, "tick clicked !");
            mEnableButtonOk = false;
            invalidateOptionsMenu();
            if (checkInputs()) {
                if (mNewGroup) {
                    createExpense();
                } else {
                    updateExpense();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkInputs() {
        if (mExpenseAmountTV.getText().toString().length() < 1) {
            Toast.makeText(this, R.string.edit_expense_incorrect_amount, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mExpenseNameTV.getText().toString().length() < 3) {
            Toast.makeText(this, R.string.edit_expense_name_too_short, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void createExpense() {
        ArrayList<Participation> dest = new ArrayList<Participation>();
        ArrayList<Participation> parts =((ParticipationAdapter) mPartsLV.getAdapter()).getParticipations();
        for (Participation currPart : parts) {
               if (currPart.getParts() > 0) {
                   dest.add(currPart);
               }
        }

        Expense e = new Expense(mExpenseNameTV.getText().toString(), Float.parseFloat(mExpenseAmountTV.getText().toString()),
                ((Friend)mPayer.getSelectedItem()).getActorId(), dest );

        Log.d(TAG, "Creating expense with count(participations):" + e.getParticipations().size());

        Backend.getInstance(this).createExpense(this, mGroup.getId(), e);

    }

    private void updateExpense() {
        mParticipationsToProcess = 0;
        mParticipationsProcessed = 0;

        for (Participation currentPart : ((ParticipationAdapter) mPartsLV.getAdapter()).getParticipations()) {
            mParticipationsToProcess++;
            Log.d(TAG, "currentPart: " + currentPart.getGuestNick() + " partNb: " + currentPart.getParts());
            if (currentPart.getParts() > 0) {
                Backend.getInstance(this).updateParticipation(this, mExpenseId, currentPart);
            } else {
                Backend.getInstance(this).deleteParticipation(this, currentPart.getId());
            }
        }
    }


    @Override
    public void onBackendResponse(BackendObject bo) {
        if (bo instanceof GroupExpenses) {
            mGroup.setGroupExpenses((GroupExpenses) bo);
            mExpense = mGroup.getExpense(mExpenseId);
            mExpenseNameTV.setText(mExpense.getName());
            mExpenseAmountTV.setText(mExpense.getAmount() + "");
            mParticipationAdapter = new ParticipationAdapter(getApplicationContext(), mGroup, mExpense.getParticipations());
            mPartsLV.setAdapter(mParticipationAdapter);
            Backend.getInstance(getApplicationContext()).getFriends(this);
        }

        if (bo instanceof Friends) {
            mFriends = (Friends) bo;
            Log.d(TAG, "Received some friends:" + mFriends.getCount());
            mPayer.setAdapter(new FriendAdapter(this, android.R.layout.simple_spinner_item, mFriends));

            if (!mNewGroup) {
                mPayer.setSelection(((ArrayAdapter) mPayer.getAdapter()).getPosition(mExpense.getPayerNick()));
                } else {
                    ArrayList<Participation> suggestedFriends = new ArrayList<Participation>();
                    for (Friend currFriend : mFriends.getFriends()) {
                        suggestedFriends.add(new Participation(currFriend.getActorId(), 0, currFriend.getActorNick()));
                    }
                    mParticipationAdapter = new ParticipationAdapter(getApplicationContext(), mGroup, suggestedFriends);
                    mPartsLV.setAdapter(mParticipationAdapter);
            }
        }

        if (bo instanceof Participation) {
            Participation p = (Participation) bo;
            Log.d(TAG, "Received participation update:" + p.getId() );
            mParticipationsProcessed++;

            if (mParticipationsProcessed == mParticipationsToProcess) {
                Toast.makeText(this, R.string.edit_expense_update_expense_ok, Toast.LENGTH_SHORT).show();
                Backend.getInstance(this).invalidateExpenseCache(mGroup.getId());
                this.finish();
            }
        }

        if (bo instanceof ExpenseResponse) {
            Toast.makeText(this, R.string.edit_expense_create_expense_ok, Toast.LENGTH_SHORT).show();
            Backend.getInstance(this).invalidateExpenseCache(mGroup.getId());
            this.finish();
        }
    }

    @Override
    public void onBackendError(BackendException be) {

    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "Clicked view: " + view);

        if (view.getId() == R.id.edit_expense_fab) {
            ArrayList<Participation> participations = mParticipationAdapter.getParticipations();
            ArrayList<Friend> participatingFriends = Utils.extractFriendsFromParticipation(participations);

            startActivityForResult(AddFriendActivity.getIntent(this, mFriends.getFriends(), participatingFriends), ADD_A_FRIEND);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_A_FRIEND) {
            if (resultCode == RESULT_OK) {
                Friend f = data.getParcelableExtra(AddFriendActivity.getNewFriendExtraKey());
                Log.d(TAG, "Received this friend to add to participation: " + f);
            }
        }
    }
}
