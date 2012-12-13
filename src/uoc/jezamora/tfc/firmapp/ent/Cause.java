/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Classe entitat Login
 */

package uoc.jezamora.tfc.firmapp.ent;

import android.os.Parcel;
import android.os.Parcelable;

public class Cause implements Parcelable {

	private int idCause;
	private String nameC;
	private String descC;
	private String beginC;
	private String endC;
	private String totalC;
	private String needC;

	public Cause(int idCause, String nameC, String descC, String beginC,
			String endC, String totalC, String needC) {
		setidCause(idCause);
		setnameC(nameC);
		setdescC(descC);
		setbeginC(beginC);
		setendC(endC);
		settotalC(totalC);
		setneedC(needC);
	}

	//setters
	private void setidCause(int idCause) {
		this.idCause = idCause;
	}

	private void setnameC(String nameC) {
		this.nameC = nameC;
	}

	private void setdescC(String descC) {
		this.descC = descC;
	}

	private void setbeginC(String beginC) {
		this.beginC = beginC;
	}

	private void setendC(String endC) {
		this.endC = endC;
	}

	private void settotalC(String totalC) {
		this.totalC = totalC;
	}

	private void setneedC(String needC) {
		this.needC = needC;
	}

	//getters
	public int getidCause() {
		return this.idCause;
	}

	public String getnameC() {
		return this.nameC;
	}

	public String getdescC() {
		return this.descC;
	}

	public String getbeginC() {
		return this.beginC;
	}

	public String getendC() {
		return this.endC;
	}

	public String gettotalC() {
		return this.totalC;
	}

	public String getneedC() {
		return this.needC;
	}

	@Override
	public int describeContents() {

		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(getidCause());
		out.writeString(getnameC());
		out.writeString(getdescC());
		out.writeString(getbeginC());
		out.writeString(getendC());
		out.writeString(gettotalC());
		out.writeString(getneedC());

	}

	public static final Parcelable.Creator<Cause> CREATOR = new Parcelable.Creator<Cause>() {
		public Cause createFromParcel(Parcel in) {
			return new Cause(in);
		}

		public Cause[] newArray(int size) {
			return new Cause[size];
		}
	};

	private Cause(Parcel in) {
		setidCause(in.readInt());
		setnameC(in.readString());
		setdescC(in.readString());
		setbeginC(in.readString());
		setendC(in.readString());
		settotalC(in.readString());
		setneedC(in.readString());
	}
}
