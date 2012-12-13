/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Classe entitat Preferences
 */

package uoc.jezamora.tfc.firmapp.ent;

public class Preferences {
	Integer id;
	String usr;
	String pwd;
	String token;
	
	public Preferences(int id, String usr, String pwd, String token) {
		setId(id);
		setUsr(usr);
		setPwd(pwd);
		setToken(token);
	}
	
	//setters
	private void setId(Integer id) {
		this.id = id;
	}	
	private void setUsr(String usr) {
		this.usr = usr;
	}
	
	private void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	private void setToken(String token) {
		this.token = token;
	}
	//getters
	public Integer getId() {
		return this.id;
	}
	
	public String getUsr() {
		return this.usr;
	}
	
	public String getPwd() {
		return this.pwd;
	}
	
	public String getToken() {
		return this.token;
	}
}
