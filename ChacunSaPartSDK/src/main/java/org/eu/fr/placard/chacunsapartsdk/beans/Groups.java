package org.eu.fr.placard.chacunsapartsdk.beans;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class Groups extends BackendObject {
	@SerializedName("data")
	private ArrayList<Group> mGroups;
	
	public Groups() {
		
	}

	public ArrayList<Group> getGroups() {
		return mGroups;
	}

	public void setGroups(ArrayList<Group> mGroups) {
		this.mGroups = mGroups;
	}
	
	public int getSize() {
		return mGroups.size();
	}
}
