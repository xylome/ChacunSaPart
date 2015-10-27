package placard.fr.eu.org.chacunsapart.backend.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Group implements Parcelable {
	@SerializedName("group_id")
	private int mId;
	
	@SerializedName("group_name")
	private String mName;
	
	@SerializedName("group_fraction")
	private int mFraction;
	
	@SerializedName("creator_id")
	private int mCreatorId;
	
	@SerializedName("creator_nick")
	private String mCreatorNick;

    private GroupExpenses mGroupExpenses;

	public Group() {
		
	}

	public Group(String name, int fraction, int creator_id) {
        mName = name;
        mFraction = fraction;
        mCreatorId = creator_id;
    }

	public int getId() {
		return mId;
	}

	public void setId(int mId) {
		this.mId = mId;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public int getFraction() {
		return mFraction;
	}

	public void setFraction(int mFraction) {
		this.mFraction = mFraction;
	}

	public int getCreatorId() {
		return mCreatorId;
	}

	public void setCreatorId(int mCreatorId) {
		this.mCreatorId = mCreatorId;
	}

	public String getCreatorNick() {
		return mCreatorNick;
	}

	public void setCreatorNick(String mCreatorNick) {
		this.mCreatorNick = mCreatorNick;
	}

    public void setGroupExpenses(GroupExpenses ge) {
        mGroupExpenses = ge;
    }

    public GroupExpenses getGroupExpenses() {
        return mGroupExpenses;
    }

	public String toString() {
		return mName;
	}

	public Expense getExpense(int expense_id) {
        return mGroupExpenses.getExpense(expense_id);
    }

	public Group(Parcel in) {
        this.mId = in.readInt();
        this.mName = in.readString();
        this.mFraction = in.readInt();
        this.mCreatorId = in.readInt();
        this.mCreatorNick = in.readString();
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeInt(mFraction);
        parcel.writeInt(mCreatorId);
        parcel.writeString(mCreatorNick);
	}

    public static final Creator CREATOR = new Creator() {
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
}
