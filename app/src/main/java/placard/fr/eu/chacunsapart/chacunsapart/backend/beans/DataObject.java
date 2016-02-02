package placard.fr.eu.chacunsapart.chacunsapart.backend.beans;

import com.google.gson.annotations.SerializedName;

public class DataObject {
	@SerializedName("status")
	private String mData;
		
	public DataObject() {
		
	}
	
	public String getData() {
		return mData;
	}
}
