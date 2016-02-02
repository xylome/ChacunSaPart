package placard.fr.eu.chacunsapart.backend.beans;

import com.google.gson.annotations.SerializedName;

public class BackendObject {
	private static final Object STATUS_OK = "OK";
	
	@SerializedName("status")	
	protected String mStatus;

	public BackendObject() {
		
	}
	
	public void setStatus(String status) {
		mStatus = status;
	}
	
	public String getStatus() {
		return mStatus;
	}
	
	public boolean isOk() {
		return STATUS_OK.equals(mStatus);
	}
}
