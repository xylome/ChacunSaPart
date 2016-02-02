package placard.fr.eu.chacunsapart.ui;

import placard.fr.eu.chacunsapart.backend.exceptions.BackendException;
import placard.fr.eu.chacunsapart.backend.exceptions.BadCredentialsException;
import placard.fr.eu.chacunsapart.backend.exceptions.HttpConnectionException;
import placard.fr.eu.chacunsapart.backend.lib.Backend;
import placard.fr.eu.chacunsapart.backend.beans.BackendObject;
import placard.fr.eu.chacunsapart.backend.listeners.BackendListener;


import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import placard.fr.eu.org.chacunsapart.R;

public class MainActivity extends AppCompatActivity implements OnClickListener, BackendListener {

    private static final String TAG = MainActivity.class.getSimpleName();

	private Button mLoginBtn;

	
	private EditText mLoginEt;
	private EditText mPasswordEt;

	private ActionBar mAction;

	/* The backend */
	private Backend mBackend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mBackend = Backend.getInstance(getApplicationContext());
		
		mLoginBtn = (Button) findViewById(R.id.login);
		mLoginBtn.setOnClickListener(this);
		

		
		mLoginEt = (EditText) findViewById(R.id.login_et);
		mPasswordEt = (EditText) findViewById(R.id.password_et);

		mAction = getSupportActionBar();

		mAction.setTitle(R.string.title_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() --------");

		if (mBackend.getEmail() != null) {
			mLoginEt.setText(mBackend.getEmail());
		}

		if (mBackend.isLoggedIn()) {
			startActivity(MyGroupsActivity.getIntent(getApplicationContext()));
			finish();
		}
    }

	@Override
	public void onClick(View v) {
        System.out.println(" !!!!!!!!!!! onClick: clicked !");
		switch(v.getId()) {
		case R.id.login:
			mBackend.login(this, mLoginEt.getText().toString(), mPasswordEt.getText().toString());
			break;
		}	
	}
	
	@Override
	public void onBackendResponse(BackendObject bo) {
		Log.d("Main", "BackendResponse said ok");
		Log.d("Main", "Email is: " + mBackend.getEmail() + " Nick is: " + mBackend.getNick());
		startActivity(MyGroupsActivity.getIntent(getApplicationContext()));
		finish();
	}

	@Override
	public void onBackendError(BackendException be) {
		if (be instanceof HttpConnectionException) {
			Toast.makeText(getApplicationContext(), "Network problem", Toast.LENGTH_SHORT).show();
		}
		
		if (be instanceof BadCredentialsException) {
			Toast.makeText(getApplicationContext(), "Bad credentials", Toast.LENGTH_SHORT).show();
		}
	}
}
