/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Classe entitat Firm
 */

package uoc.jezamora.tfc.firmapp.ent;


public class Firm {
	private int idUser;
	private int idCause;
	private String firm;

	public Firm(int idUser, int idCause, String firm) {
		setidUser(idUser);
		setidCause(idCause);
		setFirm(firm);

	}
	//setters
	private void setidUser(int idUser) {
		this.idUser = idUser;
	}

	private void setidCause(int idCause) {
		this.idCause = idCause;
	}

	private void setFirm(String firm) {
		this.firm = firm;
	}

	//getters
	public int getidUser() {
		return idUser;
	}

	public int getidCause() {
		return idCause;
	}

	public String getfirm() {
		return firm;
	}

}
