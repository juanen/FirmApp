/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Manage class user profile
 */

package uoc.jezamora.tfc.firmapp.mng;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import uoc.jezamora.tfc.firmapp.ent.Session;
import uoc.jezamora.tfc.firmapp.ent.User;
import uoc.jezamora.tfc.firmapp.swb.WebServiceMngr;

public class UserMngr {

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "userid";
	private static String KEY_NAME = "name";
	private static String KEY_NIF = "nif";
	private static String KEY_SURNAME = "surname";
	private static String KEY_PROV = "province";
	private static String KEY_BORN = "date_born";
	private static String KEY_EMAIL = "email";

	private static String ACTI = "1";
	private static String NO_ACTI = "13";
	private static String DUPLI = "12";
	private static String NO_UPDATE = "19";

	private User user;
	private Session session;

	public UserMngr(User user, Context context) {
		setUser(user);

	}

	/*
	 * Getters i setters
	 */
	private void setSession(Session session) {
		this.session = session;
	}

	private void setUser(User user) {
		this.user = user;
	}

	private User getUser() {
		return user;
	}

	/*
	 * load user
	 */
	public User loadUser(String idUser) {

		WebServiceMngr wsMngr = new WebServiceMngr();

		JSONObject json = wsMngr.loadUser(idUser);

		try {
			// check for response
			if (!json.getString(KEY_SUCCESS).equals("")
					|| !json.getString(KEY_ERROR).equals("")) {
				String res = json.getString(KEY_SUCCESS);
				if (res.equals(ACTI)) {

					JSONObject json_user = json.getJSONObject("user");

					user = new User(json_user.getInt(KEY_UID),
							json_user.getString(KEY_NIF),
							json_user.getString(KEY_NAME),
							json_user.getString(KEY_SURNAME),
							json_user.getString(KEY_PROV),
							json_user.getString(KEY_BORN),
							json_user.getString(KEY_EMAIL), "");
					setUser(user);

				} else if ((json.getString(KEY_ERROR).equals(NO_ACTI))
						|| (json.getString(KEY_ERROR).equals(DUPLI))) {

					session = new Session(0, "", "", json.getString(KEY_ERROR),
							json.getString(KEY_ERROR_MSG));
					setSession(session);
					user = null;
					return null;
				}
			} else {
				user = null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return user;
	}

	/*
	 * Create user
	 */
	public Session newUser(User user) {

		WebServiceMngr wsMngr = new WebServiceMngr();

		JSONObject json = wsMngr.newUser(getUser().getnif(), getUser()
				.getuserName(), getUser().getSurname(),
				getUser().getProvince(), getUser().getFec(), getUser()
						.geteMail(), getUser().getpassword());
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

				} else if ((json.getString(KEY_ERROR).equals(NO_ACTI))
						|| (json.getString(KEY_ERROR).equals(DUPLI))) {

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
	 * update user
	 */
	public String updateUser(User user) {

		String update = "";
		WebServiceMngr wsMngr = new WebServiceMngr();

		JSONObject json = wsMngr.updateUser(String.valueOf(getUser()
				.getuserId()), getUser().getuserName(), getUser().getSurname(),
				getUser().getProvince(), getUser().getFec());
		try {
			if (!json.getString(KEY_SUCCESS).equals("")
					|| !json.getString(KEY_ERROR).equals("")) {
				String res = json.getString(KEY_SUCCESS);
				if (res.equals(ACTI)) {

					update = json.getString(KEY_SUCCESS);

				} else if ((json.getString(KEY_ERROR).equals(NO_UPDATE))
						|| (json.getString(KEY_ERROR).equals(DUPLI))) {
					update = json.getString(KEY_ERROR_MSG);

				}
			} else {
				update = "";
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return update;
	}

	/*
	 * Validate new user
	 */
	public String validateUserData(boolean pwdCheck) {

		if (getUser().getuserName().compareTo("") == 0) {
			return "Nombre usuario sin informar";
		}

		if (getUser().getSurname().compareTo("") == 0) {
			return "Campo apellido sin informar";
		}
		if (getUser().getnif().compareTo("") == 0) {
			return "Campo NIF sin informar";
		}
		if (getUser().getProvince().compareTo("") == 0) {
			return "Campo provincia sin informar";
		}
		if (getUser().getFec().compareTo("") == 0) {
			return "Campo fecha de nacimiento sin informar";
		}
		if (getUser().geteMail().compareTo("") == 0) {
			return "Campo email sin informar";
		}
		if (getUser().getpassword().compareTo("") == 0) {
			return "Campo contrase–a sin informar";
		}
		if (!getUser().geteMail().toString()
				.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
			return "Formato email inv‡lido";
		}

		else
			return null;
	}

	/*
	 * Modify user validate
	 */
	public String validateModiUser(boolean pwdCheck) {

		if (getUser().getuserName().compareTo("") == 0) {
			return "Nombre usuario vacio";
		}

		if (getUser().getSurname().compareTo("") == 0) {
			return "Campo apellido sin informar";
		}

		if (getUser().getProvince().compareTo("") == 0) {
			return "Campo provincia sin informar";
		}
		if (getUser().getFec().compareTo("") == 0) {
			return "Campo fecha de nacimiento sin informar";
		}

		else
			return null;
	}
}
