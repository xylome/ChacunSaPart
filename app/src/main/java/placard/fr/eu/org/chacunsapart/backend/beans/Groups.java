package placard.fr.eu.org.chacunsapart.backend.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Groups extends BackendObject {
	@SerializedName("data")
	private ArrayList<Group> mGroups = new ArrayList<Group>();

	public Groups() {

	}

	public ArrayList<Group> getGroups() {
		return mGroups;
	}

	public void setGroups(ArrayList<Group> mGroups) {
		this.mGroups = mGroups;
	}
	
	public int getSize() {
		if (mGroups != null) {
			return mGroups.size();
		} else {
			return 0;
		}
	}
}
