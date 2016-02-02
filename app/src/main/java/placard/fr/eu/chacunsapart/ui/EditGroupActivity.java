package placard.fr.eu.chacunsapart.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import placard.fr.eu.chacunsapart.backend.exceptions.BackendException;
import placard.fr.eu.chacunsapart.adapters.PartAdapter;
import placard.fr.eu.chacunsapart.backend.beans.BackendObject;
import placard.fr.eu.chacunsapart.backend.beans.Dividor;
import placard.fr.eu.chacunsapart.backend.beans.Group;
import placard.fr.eu.chacunsapart.backend.lib.Backend;
import placard.fr.eu.chacunsapart.backend.listeners.BackendListener;
import placard.fr.eu.org.chacunsapart.R;

public class EditGroupActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, BackendListener{

    private static final int MIN_GROUP_NAME_SIZE = 3;

    private static final String TAG = EditGroupActivity.class.getSimpleName();

    private Spinner mSpinner;

    private Button mOk;

    private Button mCancel;

    private TextView mGroupName;

    private Dividor mSelectedDividor;

    private TextWatcher mOkButtonEnabler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_group);

        mSpinner = (Spinner) findViewById(R.id.edit_group_parts);

        mOk = (Button) findViewById(R.id.edit_group_ok_btn);
        mOk.setOnClickListener(this);
        mOk.setEnabled(false);

        mCancel = (Button) findViewById(R.id.edit_group_cancel_btn);
        mCancel.setOnClickListener(this);

        mGroupName = (TextView) findViewById(R.id.edit_group_name_tv);

        PartAdapter adapter = new PartAdapter(this, android.R.layout.simple_spinner_item);

        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(this);

        mOkButtonEnabler = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() < MIN_GROUP_NAME_SIZE) {
                    mOk.setEnabled(false);
                } else {
                    mOk.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        mGroupName.addTextChangedListener(mOkButtonEnabler);
    }

    public static Intent getIntent(Context c) {
        Intent i = new Intent(c, EditGroupActivity.class);
        return i;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d(TAG, "onOptionsItemSelected: item:" + item.getItemId());
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        Log.d(TAG, "selected pos is: " + pos);
        mSelectedDividor = (Dividor) parent.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "Clicked is: " + view.getId());
        switch (view.getId()) {
            case R.id.edit_group_ok_btn:
                Log.d(TAG, "Have to create a group name is: " + mGroupName.getText() + ", min part is: " + mSelectedDividor.getValue());
                mOk.setEnabled(false);
                mCancel.setEnabled(false);
                Group newGroup = new Group(mGroupName.getText().toString(), mSelectedDividor.getValue(), Backend.getInstance(getApplicationContext()).getActorId());
                Backend.getInstance(getApplicationContext()).createGroup(this, newGroup);
                break;
            case R.id.edit_group_cancel_btn:
                this.finish();
            default:
                break;
        }
    }

    @Override
    public void onBackendResponse(BackendObject bo) {
        // Succes !! Go away.
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onBackendError(BackendException be) {
        Log.d(TAG, "Creation failed: " + be.getMessage());
        mOk.setEnabled(true);
        mCancel.setEnabled(true);
    }
}
