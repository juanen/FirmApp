/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Manage class user causes
 */
package uoc.jezamora.tfc.firmapp.mng;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import uoc.jezamora.tfc.firmapp.swb.*;

public class UserCauseMngr {

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String ACTI = "1";
	private static String NO_ACTI = "11";

	public UserCauseMngr(Context context) {

	}

	/*
	 * drop firm
	 */
	public boolean dropFirm(String idUser, String idCause) {
		WebServiceMngr wsMngr = new WebServiceMngr();

		JSONObject json = wsMngr.dropfirm(idUser, idCause);
		boolean state = false;
		// check for response
		try {
			if (!json.getString(KEY_SUCCESS).equals("")
					|| !json.getString(KEY_ERROR).equals("")) {
				String res = json.getString(KEY_SUCCESS);
				if (res.equals(ACTI)) {

					state = true;

				} else if (json.getString(KEY_ERROR).equals(NO_ACTI)) {

					state = false;

				}
			} else {
				state = false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return state;
	}

	/*
	 * Validate if user had sign the cause
	 */
	public boolean validateFirm(String idUser, String idCause) {

		WebServiceMngr wsMngr = new WebServiceMngr();
		JSONObject json = wsMngr.validatefirm(idUser, idCause);
		boolean state = false;
		try {
			//check for response
			if (!json.getString(KEY_SUCCESS).equals("")
					|| !json.getString(KEY_ERROR).equals("")) {
				String res = json.getString(KEY_SUCCESS);
				if (res.equals(ACTI)) {

					state = true;

				} else if (json.getString(KEY_ERROR).equals(NO_ACTI)) {

					state = false;

				}
			} else {
				state = false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return state;
	}

}
