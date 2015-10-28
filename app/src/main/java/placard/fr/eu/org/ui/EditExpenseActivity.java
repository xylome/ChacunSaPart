package placard.fr.eu.org.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import placard.fr.eu.org.adapters.ParticipationAdapter;
import placard.fr.eu.org.chacunsapart.backend.beans.BackendObject;
import placard.fr.eu.org.chacunsapart.backend.beans.Expense;
import placard.fr.eu.org.chacunsapart.backend.beans.Group;
import placard.fr.eu.org.chacunsapart.backend.beans.GroupExpenses;
import placard.fr.eu.org.chacunsapart.backend.exceptions.BackendException;
import placard.fr.eu.org.chacunsapart.backend.lib.Backend;
import placard.fr.eu.org.chacunsapart.backend.listeners.BackendListener;
import placard.fr.eu.org.chacunsapart.backend.listeners.BackendResponse;
import placard.fr.eu.org.chacunsaparttesteur.R;

public class EditExpenseActivity extends AppCompatActivity implements BackendListener, View.OnClickListener {

    private static final String EXPENSE_FIELD = "expense_field";

    private static final String GROUP_FIELD = "group_field";

    private static final String TAG = EditExpenseActivity.class.getSimpleName();

    private int mExpenseId;

    private Group mGroup;

    private Expense mExpense;

    private ActionBar mActionBar;

    private EditText mExpenseNameTV;

    private EditText mExpenseAmountTV;

    // TODO Add the friends spinner
    private Spinner mPayer;

    private ListView mPartsLV;

    private Button mOk;

    private Button mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        mActionBar = getSupportActionBar();
        Bundle extras = getIntent().getExtras();
        mGroup = extras.getParcelable(GROUP_FIELD);
        mExpenseId = extras.getInt(EXPENSE_FIELD);
        mActionBar.setTitle(R.string.edit_expense);

        mExpenseNameTV = (EditText) findViewById(R.id.edit_expense_name_tv);

        mExpenseAmountTV = (EditText) findViewById(R.id.edit_expense_amount_tv);

        mPartsLV = (ListView) findViewById(R.id.edit_expense_participations_lv);

        mOk = (Button) findViewById(R.id.edit_expense_ok_btn);
        mOk.setOnClickListener(this);

        mCancel = (Button) findViewById(R.id.edit_expense_cancel_btn);
        mCancel.setOnClickListener(this);

        Backend.getInstance(getApplicationContext()).getExpenses(this, mGroup.getId());
    }

    public static Intent getIntent(Context c, int expense_id, Group group) {
        Intent i = new Intent(c, EditExpenseActivity.class);
        i.putExtra(EXPENSE_FIELD, expense_id);
        i.putExtra(GROUP_FIELD, group);
        return i;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_expense, menu);
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

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackendResponse(BackendObject bo) {
        if (bo instanceof GroupExpenses) {
            mGroup.setGroupExpenses((GroupExpenses) bo);
            mExpense = mGroup.getExpense(mExpenseId);
            mExpenseNameTV.setText(mExpense.getName());
            mExpenseAmountTV.setText(mExpense.getAmount() + "");
            mPartsLV.setAdapter(new ParticipationAdapter(getApplicationContext(), mGroup, mExpense.getParticipations()));
        }
    }

    @Override
    public void onBackendError(BackendException be) {

    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "Clicked view: " + view);
        switch(view.getId()) {
            case R.id.edit_expense_cancel_btn:
                finish();
                break;
            case R.id.edit_expense_ok_btn:

                break;
        }
    }
}
