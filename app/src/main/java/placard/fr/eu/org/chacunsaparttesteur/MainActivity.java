package placard.fr.eu.org.chacunsaparttesteur;

import org.eu.fr.placard.chacunsapartsdk.exceptions.BackendException;
import org.eu.fr.placard.chacunsapartsdk.exceptions.BadCredentialsException;
import org.eu.fr.placard.chacunsapartsdk.exceptions.HttpConnectionException;
import org.eu.fr.placard.chacunsapartsdk.beans.BackendObject;
import org.eu.fr.placard.chacunsapartsdk.lib.Backend;
import org.eu.fr.placard.chacunsapartsdk.listeners.BackendListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, BackendListener {

    private static final String TAG = MainActivity.class.getSimpleName();

	private Button mLoginBtn;
	private Button mLogoutBtn;
	private Button mNextBtn;
	
	private EditText mLoginEt;
	private EditText mPasswordEt;

	/* The backend */
	private Backend mBackend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mBackend = Backend.getInstance(getApplicationContext());
		
		mLoginBtn = (Button) findViewById(R.id.login);
		mLoginBtn.setOnClickListener(this);
		
		mLogoutBtn = (Button) findViewById(R.id.logout);
		mLogoutBtn.setOnClickListener(this);
		
		mNextBtn = (Button) findViewById(R.id.next);
		mNextBtn.setOnClickListener(this);
		
		mLoginEt = (EditText) findViewById(R.id.login_et);
		mPasswordEt = (EditText) findViewById(R.id.password_et);
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
        Log.d(TAG, "onResume()");
    }

	@Override
	public void onClick(View v) {
        System.out.println(" !!!!!!!!!!! onClick: clicked !");
		switch(v.getId()) {
		case R.id.login:
			mBackend.login(this, mLoginEt.getText().toString(), mPasswordEt.getText().toString());
			break;
		case R.id.logout:
			mBackend.logout();
			break;
		case R.id.next:
			Intent i = MyGroups.getIntent(getApplicationContext());
			startActivity(i);
			break;
		}	
	}
	
	@Override
	public void onBackendResponse(BackendObject bo) {
		Log.d("Main", "BackendResponse said ok");
		Log.d("Main", "Email is: " + mBackend.getEmail() + " Nick is: " + mBackend.getNick());
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
