/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Classe entitat Login
 */

package uoc.jezamora.tfc.firmapp.ent;

public class Login {

	private String usr;
	private String pwd;
	private String token;
	
	public Login(String usr, String pwd, String token) {
		setUsr(usr);
		setPwd(pwd);
		setToken(token);
	}
	//setters
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
