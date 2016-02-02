package placard.fr.eu.chacunsapart.ui;

import placard.fr.eu.chacunsapart.chacunsapart.backend.beans.BackendObject;
import placard.fr.eu.chacunsapart.chacunsapart.backend.beans.Group;
import placard.fr.eu.chacunsapart.chacunsapart.backend.beans.Groups;
import placard.fr.eu.chacunsapart.chacunsapart.backend.exceptions.BackendException;
import placard.fr.eu.chacunsapart.chacunsapart.backend.lib.Backend;
import placard.fr.eu.chacunsapart.chacunsapart.backend.listeners.BackendListener;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import placard.fr.eu.org.chacunsaparttesteur.R;

public class MyGroupsActivity extends AppCompatActivity implements BackendListener,
        View.OnClickListener {

	private static final String TAG = MyGroupsActivity.class.getSimpleName();
    private static final int CREATE_GROUP = 0x00101;
    private static final int UPDATE_GROUP = 0X00102;
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
        //mFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.csp_blue)));

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
        mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "Long clicked group is: " + adapterView.getItemAtPosition(i).toString());
                Toast.makeText(getApplicationContext(), "Server does not support group update.", Toast.LENGTH_LONG).show();
                return true;
            }
        });

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
	public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_GROUP || requestCode == UPDATE_GROUP) {
            if (resultCode == Activity.RESULT_OK) {
                mBackend.getMyGroups(this, true);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.my_groups_fab) {
            Intent i = EditGroupActivity.getIntent(this);
            startActivityForResult(i, CREATE_GROUP);
        }
    }
}
