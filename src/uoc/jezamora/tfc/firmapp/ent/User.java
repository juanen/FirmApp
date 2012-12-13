/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Classe entitat User
 */

package uoc.jezamora.tfc.firmapp.ent;


public class User {
	private int userId;
	private String userName;
	private String surname;
	private String nif;
	private String eMail;
	private String idNumber;
	private String password;
	private String password2;
	private String province;
	private String fec;
	
	public User (int userId, String nif, String userName, String surname, String province, String fec,
			String eMail, String password) {
		setUserId(userId);
		setUserName(userName);
		setSurname(surname);
		setnif(nif);
		setEmail(eMail);
		setPassword(password);
		setProvince(province);
		setFec(fec);
	}

	//setters
	private void setUserId(int userId) {
		this.userId = userId;
	}
	
	private void setUserName(String userName) {
		this.userName = userName;
	}

	private void setSurname(String surname) {
		this.surname = surname;
	}

	private void setnif(String nif) {
		this.nif = nif;
	}

	private void setEmail(String eMail) {
		this.eMail = eMail;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	private void setPassword(String password) {
		this.password = password;
	}

	private void setProvince(String province) {
		this.province = province;
	}
	
	private void setFec(String fec) {
		this.fec = fec;
	}	
	
	//getters
	public int getuserId() {
		return userId;
	}
	
	public String getuserName() {
		return userName;
	}

	public String getSurname() {
		return surname;
	}

	public String getnif() {
		return nif;
	}

	public String geteMail() {
		return eMail;
	}

	public String getidNumber() {
		return idNumber;
	}

	public String getpassword() {
		return password;
	}

	public String getpassword2() {
		return password2;
	}	
	public String getProvince() {
		return province;
	}

	public String getFec() {
		return fec;
	}

}
