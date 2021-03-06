package placard.fr.eu.org.chacunsaparttesteur;

import org.eu.fr.placard.chacunsapartsdk.exceptions.BackendException;
import org.eu.fr.placard.chacunsapartsdk.beans.BackendObject;
import org.eu.fr.placard.chacunsapartsdk.beans.Group;
import org.eu.fr.placard.chacunsapartsdk.beans.Groups;
import org.eu.fr.placard.chacunsapartsdk.lib.Backend;
import org.eu.fr.placard.chacunsapartsdk.listeners.BackendListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyGroups extends Activity implements BackendListener {

	private static final String TAG = MyGroups.class.getSimpleName();
	private Backend mBackend;
	private ListView mList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_groups);
		
		mBackend = Backend.getInstance(getApplicationContext());
		mBackend.myGroups(this);
		
		mList = (ListView) findViewById(R.id.my_groups_lv);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_groups, menu);
		return true;
	}

	public static Intent getIntent(Context c) {
		Intent i = new Intent(c, MyGroups.class);
		return i;
	}

	@Override
	public void onBackendResponse(BackendObject bo) {
		Groups myGroups = (Groups) bo;
		Log.d(TAG, "onBackendResponse: groups.size: " + myGroups.getSize());
		
		final ArrayAdapter<Group> adapter = new ArrayAdapter<Group>(getApplicationContext(), android.R.layout.simple_list_item_1, myGroups.getGroups());
		mList.setAdapter(adapter);
	}

	@Override
	public void onBackendError(BackendException be) {
		
	}
	
}
