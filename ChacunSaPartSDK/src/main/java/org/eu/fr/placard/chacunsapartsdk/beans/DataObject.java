package org.eu.fr.placard.chacunsapartsdk.beans;

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
