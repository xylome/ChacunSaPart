package placard.fr.eu.chacunsapart.chacunsapart.backend.beans;

import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BackendObject {

	@SerializedName("acct_id")
	private String mAccountId ;
	
	@SerializedName("nick")
	private String mNick;
	
	@SerializedName("actor_id")
	private int mActorId;
	
	@SerializedName("email")
	private String mEmail;
	
	@SerializedName("auth_cookie")
	private String mAuthCookie;
	
	public LoginResponse() {
		
	}

	public String getAccountId() {
		return mAccountId;
	}

	public void setAccountId(String accountId) {
		this.mAccountId = accountId;
	}

	public String getNick() {
		return mNick;
	}

	public void setNick(String nick) {
		this.mNick = nick;
	}

	public int getActorId() {
		return mActorId;
	}

	public void setActorId(int actorId) {
		this.mActorId = actorId;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String email) {
		this.mEmail = email;
	}

	public String getAuthCookie() {
		return mAuthCookie;
	}

	public void setAuthCookie(String authCookie) {
		this.mAuthCookie = authCookie;
	}
	
	public boolean loginSuccess() {
		return this.isOk();
	}
	
	@Override
	public String toString() {
		return "Login response, account_id:" + mAccountId + ", nick:" + mNick;
	}
	
}
