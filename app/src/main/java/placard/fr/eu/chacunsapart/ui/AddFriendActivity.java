package placard.fr.eu.chacunsapart.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import placard.fr.eu.chacunsapart.backend.beans.Friend;

import java.util.ArrayList;

import placard.fr.eu.chacunsapart.utils.Utils;
import placard.fr.eu.chacunsapart.adapters.AutoCompleteAdapter;

import placard.fr.eu.org.chacunsapart.R;

public class AddFriendActivity extends AppCompatActivity {
    private static final String ALL_FRIENDS = "extra_all_friends";
    private static final String PARTICIPATING_FRIENDS = "extra_participating_friends";
    private static final String NEW_FRIEND = "extra_new_friend";

    private AppCompatAutoCompleteTextView mAutoComplete;
    private TextView mStatus;
    private boolean mEnableButton = false;

    private TextWatcher mCheckStatus = null;
    private ArrayList<Friend> mAllFriends = null;
    private ArrayList<Friend> mParticipatingFriends = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        Bundle extras = getIntent().getExtras();
        setUpMembers(extras);
        setUpViews();
        setUpActionBar();
    }

    private void setUpMembers(Bundle extras) {
        mAllFriends =  extras.getParcelableArrayList(ALL_FRIENDS);
        mParticipatingFriends = extras.getParcelableArrayList(PARTICIPATING_FRIENDS);
    }

    private void setUpViews() {
        mAutoComplete = (AppCompatAutoCompleteTextView) findViewById(R.id.add_friend_autocomplete);
        AutoCompleteAdapter autoCompleteAdapter = new AutoCompleteAdapter(this, R.layout.auto_complete, mAllFriends, mParticipatingFriends);
        mAutoComplete.setAdapter(autoCompleteAdapter);
        mStatus = (TextView) findViewById(R.id.auto_complete_friend_status);
        mStatus.setText(R.string.ready);
        setUpTextWatcher();
        mAutoComplete.addTextChangedListener(mCheckStatus);
    }

    private void setUpTextWatcher() {
        mCheckStatus = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                processTextChanged(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }

    private void processTextChanged(CharSequence charSequence) {
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

    private void setUpActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_friend_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.add_friend_title);
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
        imm.hideSoftInputFromWindow(mAutoComplete.getWindowToken(), 0);
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
            sendResult();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendResult() {
        deactivateOk();
        Intent i = new Intent();
        Friend f = Utils.resolveFriend(mAutoComplete.getText().toString(), mAllFriends);
        i.putExtra(NEW_FRIEND, f);
        setResult(RESULT_OK, i);
        finish();
    }

}
