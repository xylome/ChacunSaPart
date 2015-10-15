package placard.fr.eu.org.ui;

import placard.fr.eu.org.chacunsapart.backend.exceptions.BackendException;
import placard.fr.eu.org.chacunsapart.backend.beans.BackendObject;
import placard.fr.eu.org.chacunsapart.backend.beans.Group;
import placard.fr.eu.org.chacunsapart.backend.beans.Groups;
import placard.fr.eu.org.chacunsapart.backend.lib.Backend;
import placard.fr.eu.org.chacunsapart.backend.listeners.BackendListener;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import placard.fr.eu.org.chacunsaparttesteur.R;

public class MyGroupsActivity extends AppCompatActivity implements BackendListener,
        View.OnClickListener {

	private static final String TAG = MyGroupsActivity.class.getSimpleName();
	private Backend mBackend;
	private ListView mList;
	private FloatingActionButton mFab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_groups);

		mList = (ListView) findViewById(R.id.my_groups_lv);

		mBackend = Backend.getInstance(getApplicationContext());

		if (mBackend.isLoggedIn()) {
			mBackend.getMyGroups(this);
		}

        mFab = (FloatingActionButton) findViewById(R.id.my_groups_fab);

        mFab.setOnClickListener(this);

		getSupportActionBar().setTitle(R.string.title_activity_my_groups);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_groups, menu);
		return true;
	}

	public static Intent getIntent(Context c) {
		Intent i = new Intent(c, MyGroupsActivity.class);
		return i;
	}

	@Override
	public void onBackendResponse(BackendObject bo) {
		Groups myGroups = (Groups) bo;
		Log.d(TAG, "onBackendResponse: groups.size: " + myGroups.getSize());
		
		final ArrayAdapter<Group> adapter = new ArrayAdapter<Group>(this, android.R.layout.simple_list_item_1, android.R.id.text1, myGroups.getGroups());
		mList.setAdapter(adapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "Clicked item number : " + i);
                Log.d(TAG, "adapterView item creator is : " + ((Group)adapterView.getItemAtPosition(i)).getCreatorNick());
                startActivity(GroupActivity.getIntent(getApplicationContext(), ((Group) adapterView.getItemAtPosition(i))));
            }
        });
    }

	@Override
	public void onBackendError(BackendException be) {
		
	}

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.my_groups_fab) {
            Intent i = EditGroupActivity.getIntent(this);
            startActivity(i);
        }
    }
}
