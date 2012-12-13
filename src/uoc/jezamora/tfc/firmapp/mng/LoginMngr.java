/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Manage class user access
 */
package uoc.jezamora.tfc.firmapp.mng;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import uoc.jezamora.tfc.firmapp.ent.Login;
import uoc.jezamora.tfc.firmapp.ent.Session;
import uoc.jezamora.tfc.firmapp.swb.*;

public class LoginMngr {

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "userid";
	private static String KEY_NAME = "name";

	private static String KEY_EMAIL = "email";

	private static String ACTI = "1";
	private static String NO_ACTI = "11";

	private Login login;
	private Session session;

	public LoginMngr(Context context) {

		

	}

	/*
	 * Getters i setters
	 */
	private void setLogin(Login login) {
		this.login = login;
	}

	private void setSession(Session session) {
		this.session = session;
	}

	private Login getLogin() {
		return this.login;
	}

	/*
	 * Begin session
	 */
	public Session Login(Login login) {
		setLogin(login);

		WebServiceMngr wsMngr = new WebServiceMngr();

		JSONObject json = wsMngr.loginUser(getLogin().getUsr(), getLogin()
				.getPwd());
		// check for login response
		try {
			if (!json.getString(KEY_SUCCESS).equals("")
					|| !json.getString(KEY_ERROR).equals("")) {
				String res = json.getString(KEY_SUCCESS);
				if (res.equals(ACTI)) {

					JSONObject json_user = json.getJSONObject("user");

					session = new Session(json_user.getInt(KEY_UID),
							json_user.getString(KEY_EMAIL),
							json_user.getString(KEY_NAME),
							json.getString(KEY_SUCCESS), "");
					setSession(session);

				} else if (json.getString(KEY_ERROR).equals(NO_ACTI)) {
					session = new Session(0, "", "", json.getString(KEY_ERROR),
							json.getString(KEY_ERROR_MSG));
					setSession(session);
					return session;

				}
			} else {
				session = null;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return session;
	}

	/*
	 * Validate user and password
	 */
	public String validaUsuari(Login login) {

		if (login.getUsr().compareTo("") == 0
				|| login.getPwd().compareTo("") == 0) {
			return "nok";
		} else {
			return null;
		}
	}

}
