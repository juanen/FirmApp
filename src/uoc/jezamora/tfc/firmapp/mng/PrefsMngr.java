/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Manage class system preferences
 */
package uoc.jezamora.tfc.firmapp.mng;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefsMngr {

	private SharedPreferences _prefs = null;
	private Editor _editor = null;
	private String _userid = "?";
	private String _useremailaddress = "?";
	private String _username = "?";
	private Boolean _status = false;
	private String _serverurlIndex = "http://juanza.comuv.com/";
	private String _serverurlImg ="http://juanza.comuv.com/";

	public PrefsMngr(Context context) {
		this._prefs = context.getSharedPreferences("PREFS_PRIVATE",
				Context.MODE_PRIVATE);
		this._editor = this._prefs.edit();
	}

	public String getValue(String key, String defaultvalue) {
		if (this._prefs == null) {
			return "?";
		}

		return this._prefs.getString(key, defaultvalue);
	}

	public void setValue(String key, String value) {
		if (this._editor == null) {
			return;
		}

		this._editor.putString(key, value);

	}

	public String getId() {
		if (this._prefs == null) {
			return "?";
		}

		this._userid = this._prefs.getString("iduser", "?");
		return this._userid;
	}

	public String getEmail() {
		if (this._prefs == null) {
			return "?";
		}

		this._useremailaddress = this._prefs.getString("emailaddress", "?");
		return this._useremailaddress;
	}

	public String getName() {
		if (this._prefs == null) {
			return "?";
		}

		this._username = this._prefs.getString("username", "?");
		return this._username;
	}
	
	public Boolean getTwitterStatus() {
		if (this._prefs == null) {
			return false;
		}

		this._status = this._prefs.getBoolean("PREF_TWITTER_STATE", false);
		return this._status;
		
	}

	public String getServerImg() {
		if (this._prefs == null) {
			return "http://juanza.comuv.com/closefirm.php?";
		}

		this._serverurlImg = this._prefs.getString("serverurlIndex",
				"http://juanza.comuv.com/closefirm.php?");
		return this._serverurlImg;
	}
	
	public String getServerIndex() {
		if (this._prefs == null) {
			return "http://juanza.comuv.com/";
		}

		this._serverurlIndex = this._prefs.getString("serverurlImg",
				"http://juanza.comuv.com/");
		return this._serverurlIndex;
	}

	public void setIdUser(String idusuario) {
		if (this._editor == null) {
			return;
		}

		this._editor.putString("iduser", idusuario);
	}

	public void setEmail(String newemail) {
		if (this._editor == null) {
			return;
		}

		this._editor.putString("emailaddress", newemail);
	}

	public void setName(String nameuser) {
		if (this._editor == null) {
			return;
		}

		this._editor.putString("username", nameuser);
	}

	public void save() {
		if (this._editor == null) {
			return;
		}
		this._editor.commit();
	}
}
