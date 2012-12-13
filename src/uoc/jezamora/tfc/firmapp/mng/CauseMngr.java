/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Manage class system and user causes
 */
package uoc.jezamora.tfc.firmapp.mng;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uoc.jezamora.tfc.firmapp.swb.WebServiceMngr;;

public class CauseMngr {

	// JSON Response node names
	private static String KEY_CAUSES = "causes";
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_PID = "idCause";
	public static String KEY_NAME = "name";
	private static String KEY_DESC = "description";
	private static String KEY_BEG = "begin";
	private static String KEY_END = "end";
	public static String KEY_TOT = "total_firm";
	public static String KEY_NED = "total_need";
	private static String ACTI = "1";
	private static String NO_ACTI = "14";

	JSONArray causes = null;

	/*
	 * List of causes
	 */
	public ArrayList<HashMap<String, String>> Causes() {

		WebServiceMngr wsMngr = new WebServiceMngr();

		JSONObject json = wsMngr.causes();
		ArrayList<HashMap<String, String>> causeList = new ArrayList<HashMap<String, String>>();
		// check for login response
		try {
			if (!json.getString(KEY_SUCCESS).equals("")
					|| !json.getString(KEY_ERROR).equals("")) {
				String res = json.getString(KEY_SUCCESS);
				if (res.equals(ACTI)) {

					causes = json.getJSONArray(KEY_CAUSES);

					for (int i = 0; i < causes.length(); i++) {
						JSONObject c = causes.getJSONObject(i);

						// Storing each json item in variable
						String id = c.getString(KEY_PID);
						String name = c.getString(KEY_NAME);
						String desc = c.getString(KEY_DESC);
						String beg = c.getString(KEY_BEG);
						String end = c.getString(KEY_END);
						String tot = c.getString(KEY_TOT);
						String ned = c.getString(KEY_NED);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						if (id.compareTo("null") != 0) {
							map.put(KEY_PID, id);
							map.put(KEY_NAME, name);
							map.put(KEY_DESC, desc);
							map.put(KEY_BEG, beg);
							map.put(KEY_END, end);
							map.put(KEY_TOT, tot);
							map.put(KEY_NED, ned);
							causeList.add(map);
						}
						
					}

				} else if (json.getString(KEY_ERROR).equals(NO_ACTI)) {
					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(KEY_ERROR, json.getString(KEY_ERROR));
					map.put(KEY_ERROR_MSG, json.getString(KEY_ERROR_MSG));
					causeList.add(map);
					return causeList;
				}
			} else {
				causeList = null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return causeList;
	}

	/*
	 * List of user causes firm
	 */
	public ArrayList<HashMap<String, String>> myCauses(String idUser) {

		WebServiceMngr wsMngr = new WebServiceMngr();

		JSONObject json = wsMngr.mycauses(idUser);
		ArrayList<HashMap<String, String>> causeList = new ArrayList<HashMap<String, String>>();
		// check for user causes firm
		try {

			if (json != null) {
				if (!json.getString(KEY_SUCCESS).equals("")
						|| !json.getString(KEY_ERROR).equals("")) {
					String res = json.getString(KEY_SUCCESS);
					if (res.equals(ACTI)) {

						causes = json.getJSONArray(KEY_CAUSES);

						for (int i = 0; i < causes.length(); i++) {
							JSONObject c = causes.getJSONObject(i);

							// Storing each json item in variable
							String id = c.getString(KEY_PID);
							String name = c.getString(KEY_NAME);
							String desc = c.getString(KEY_DESC);
							String beg = c.getString(KEY_BEG);
							String end = c.getString(KEY_END);
							String tot = c.getString(KEY_TOT);
							String ned = c.getString(KEY_NED);

							// creating new HashMap
							HashMap<String, String> map = new HashMap<String, String>();

							// adding each child node to HashMap key => value
							if (id.compareTo("null") != 0) {
								map.put(KEY_PID, id);
								map.put(KEY_NAME, name);
								map.put(KEY_DESC, desc);
								map.put(KEY_BEG, beg);
								map.put(KEY_END, end);
								map.put(KEY_TOT, tot);
								map.put(KEY_NED, ned);
								causeList.add(map);
							}
							// adding HashList to ArrayList

						}

					} else if (json.getString(KEY_ERROR).equals(NO_ACTI)) {
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(KEY_ERROR, json.getString(KEY_ERROR));
						map.put(KEY_ERROR_MSG, json.getString(KEY_ERROR_MSG));
						causeList.add(map);
						return causeList;
					}
				} else {
					causeList = null;
				}
			} else
				return causeList=null;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return causeList;
	}

}
