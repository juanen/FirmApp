/**
 * @Author Manel Herrera - UOC - PFC
 * @Date 2011/Q4 - V1.0
 *
 * Classe que modela una sessió Moodle
 */

package uoc.jezamora.tfc.firmapp.ent;

import android.os.Parcel;
import android.os.Parcelable;


public class Session implements Parcelable{

	private int userId;
	private String email;
	private String name;
	private String state;
	private String desc;
	
	//public Session (String url, String key, int client, int userId, String email, String name) {
		//setUserId(userId);
	public Session ( int userId, String email, String name, String state, String desc) {
		setUserId(userId);
		setemail(email);
		setname(name);
		setState(state);
		setDesc(desc);
	}
	
	private void setUserId (int userId) {
		this.userId = userId;
	}
	
	private void setemail (String email) {
		this.email = email;
	}
	
	private void setname (String name) {
		this.name = name;
	}

	private void setState (String state) {
		this.state = state;
	}
	
	private void setDesc (String desc) {
		this.desc = desc;
	}
	
	public int getUserId () {
		return this.userId;
	}
	
	public String getemail () {
		return this.email;
	}
	
	public String getname () {
		return this.name;
	}

	public String getState () {
		return this.state;
	}
	public String getDesc () {
		return this.desc;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(getUserId());
		out.writeString(getemail());
		out.writeString(getname());
		out.writeString(getState());
		out.writeString(getDesc());
	}
	
    public static final Parcelable.Creator<Session> CREATOR
    	= new Parcelable.Creator<Session>() {
    	public Session createFromParcel(Parcel in) {
    		return new Session(in);
    	}         
    	public Session[] newArray(int size) {
    		return new Session[size];
    	}
    };        
    
    private Session(Parcel in) {
    	setUserId(in.readInt());
    	setemail(in.readString());
    	setname(in.readString()); 
    	setState(in.readString());
    	setDesc(in.readString());
    }
}
