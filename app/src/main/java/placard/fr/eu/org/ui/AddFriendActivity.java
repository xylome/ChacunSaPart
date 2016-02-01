package placard.fr.eu.org.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.ArrayList;

import placard.fr.eu.org.Utils.Utils;
import placard.fr.eu.org.adapters.AutoCompleteAdapter;
import placard.fr.eu.org.chacunsapart.backend.beans.Friend;
import placard.fr.eu.org.chacunsapart.backend.beans.Participation;
import placard.fr.eu.org.chacunsaparttesteur.R;

public class AddFriendActivity extends AppCompatActivity {

    private static final String TAG = AddFriendActivity.class.getSimpleName();
    private static final String ALL_FRIENDS = "extra_all_friends";
    private static final String PARTICIPATING_FRIENDS = "extra_participating_friends";
    private static final String NEW_FRIEND = "extra_new_friend";

    private Toolbar mToolbar;
    private AppCompatAutoCompleteTextView mAutoComplete;
    private AppCompatAutoCompleteTextView mNickName;
    private TextView mStatus;

    private TextWatcher mCheckStatus;

    private boolean mEnableButton = false;

    private ArrayList<Friend> mAllFriends = null;
    private ArrayList<Friend> mParticipatingFriends = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        mToolbar = (Toolbar) findViewById(R.id.add_friend_toolbar);
        setSupportActionBar(mToolbar);

        mNickName = (AppCompatAutoCompleteTextView) findViewById(R.id.add_friend_autocomplete);

        Intent received = getIntent();
        Bundle extras = received.getExtras();

        mAllFriends =  extras.getParcelableArrayList(ALL_FRIENDS);
        mParticipatingFriends = extras.getParcelableArrayList(PARTICIPATING_FRIENDS);

        mAutoComplete = (AppCompatAutoCompleteTextView) findViewById(R.id.add_friend_autocomplete);
        AutoCompleteAdapter autoCompleteAdapter = new AutoCompleteAdapter(this, R.layout.auto_complete, mAllFriends, mParticipatingFriends);
        mAutoComplete.setAdapter(autoCompleteAdapter);

        mStatus = (TextView) findViewById(R.id.auto_complete_friend_status);
        mStatus.setText("Ready…");

        getSupportActionBar().setTitle(R.string.add_friend_title);

        mCheckStatus = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 2) {
                   if (alreadyParticipating(charSequence.toString())) {
                        mStatus.setText(R.string.add_friend_already_participates);
                        deactivateOk();
                   } else if (alreadyFriend(charSequence.toString())) {
                        mStatus.setText(R.string.add_friend_adding_to_participation);
                        activateOk();
                   }
                    else {
                       mStatus.setText(R.string.add_friend_creating_and_adding_friend);
                       activateOk();
                   }
                } else {
                    mStatus.setText(R.string.add_friend_bad_nickname_not_enough_chars);
                    deactivateOk();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        mAutoComplete.addTextChangedListener(mCheckStatus);
    }

    public static String getNewFriendExtraKey() {
        return NEW_FRIEND;
    }

    private void deactivateOk() {
        mEnableButton = false;
        invalidateOptionsMenu();
    }

    private void activateOk() {
        mEnableButton = true;
        invalidateOptionsMenu();
    }

    private boolean alreadyFriend(String friend) {
        boolean result = false;
        for (Friend current : mAllFriends) {
            if (current.getActorNick().toLowerCase().equals(friend.toLowerCase())) {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean alreadyParticipating(String friend) {
        boolean result = false;
        for (Friend current : mParticipatingFriends) {
            if (current.getActorNick().toLowerCase().equals(friend.toLowerCase())) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static Intent getIntent(Context c, ArrayList<Friend> allFriends, ArrayList<Friend> participatingFriends) {
        Intent i = new Intent(c, AddFriendActivity.class);
        i.putExtra(ALL_FRIENDS, allFriends);
        i.putExtra(PARTICIPATING_FRIENDS, participatingFriends);
        return i;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_expense, menu);
        menu.findItem(R.id.action_ok).setEnabled(mEnableButton);
        if (!mEnableButton) {
            menu.findItem(R.id.action_ok).getIcon().setAlpha(60);
        } else {
            menu.findItem(R.id.action_ok).getIcon().setAlpha(255);
        }
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
            Intent i = new Intent();
            Friend f = Utils.resolveFriend(mAutoComplete.getText().toString(), mAllFriends);

            i.putExtra(NEW_FRIEND, f);
            setResult(RESULT_OK, i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
