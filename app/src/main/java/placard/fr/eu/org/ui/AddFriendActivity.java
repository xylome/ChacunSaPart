package placard.fr.eu.org.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import java.util.ArrayList;
import placard.fr.eu.org.adapters.AutoCompleteAdapter;
import placard.fr.eu.org.chacunsapart.backend.beans.Friend;
import placard.fr.eu.org.chacunsaparttesteur.R;

public class AddFriendActivity extends AppCompatActivity {

    private static final String TAG = AddFriendActivity.class.getSimpleName();
    private static final String FRIENDS = "extra_friends";

    private Toolbar mToolbar;
    private AppCompatAutoCompleteTextView mAutoComplete;
    private AppCompatAutoCompleteTextView mNickName;

    private boolean mEnableButton = true;

    private ArrayList<Friend> mFriends = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        mToolbar = (Toolbar) findViewById(R.id.add_friend_toolbar);
        setSupportActionBar(mToolbar);

        mNickName = (AppCompatAutoCompleteTextView) findViewById(R.id.add_friend_autocomplete);

        Intent received = getIntent();
        Bundle extras = received.getExtras();

        mFriends =  extras.getParcelableArrayList(FRIENDS);

        mAutoComplete = (AppCompatAutoCompleteTextView) findViewById(R.id.add_friend_autocomplete);
        AutoCompleteAdapter autoCompleteAdapter = new AutoCompleteAdapter(this, R.layout.auto_complete, mFriends);
        mAutoComplete.setAdapter(autoCompleteAdapter);


        getSupportActionBar().setTitle(R.string.add_friend_title);

    }

    public static Intent getIntent(Context c, ArrayList<Friend> friends) {
        Intent i = new Intent(c, AddFriendActivity.class);
        i.putExtra(FRIENDS, friends);
        return i;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_expense, menu);
        menu.findItem(R.id.action_ok).setEnabled(mEnableButton);
        return true;
    }

    @Override
    protected void onPause() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mNickName.getWindowToken(), 0);
        super.onPause();
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
            mEnableButton = false;
            invalidateOptionsMenu();
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
