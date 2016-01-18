package placard.fr.eu.org.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import placard.fr.eu.org.chacunsaparttesteur.R;

public class AddFriendActivity extends AppCompatActivity {

    private static final String TAG = AddFriendActivity.class.getSimpleName() ;
    private android.support.v7.widget.Toolbar mToolbar;
    private AppCompatAutoCompleteTextView mAutoComplete;
    String[] languages={"Andor", "Andromede", "Android ","java","IOS","SQL","JDBC","Web services"};
    private boolean mEnableButton = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.add_friend_toolbar);
        setSupportActionBar(mToolbar);

        mAutoComplete = (AppCompatAutoCompleteTextView) findViewById(R.id.add_friend_autocomplete);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,languages);
        mAutoComplete.setAdapter(adapter);

        getSupportActionBar().setTitle(R.string.add_friend_title);
    }

    public static Intent getIntent(Context c) {
        Intent i = new Intent(c, AddFriendActivity.class);
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
