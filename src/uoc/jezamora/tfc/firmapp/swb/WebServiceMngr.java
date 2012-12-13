/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Activity connect web service
 */
package uoc.jezamora.tfc.firmapp.swb;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class WebServiceMngr {
	private JSONParser jsonParser;

	private static String URL = "http://juanza.comuv.com/wsindex.php";

	private static String login_tag = "login";
	private static String register_tag = "register";
	private static String list_cause = "listcause";
	private static String list_mycause = "mylistcause";
	private static String load_user = "loaduser";
	private static String update_tag = "updateuser";
	private static String tag_firm = "firmcause";
	private static String tag_dropfirm = "dropfirm";
	private static String tag_validafirm = "validatefirm";

	// constructor
	public WebServiceMngr() {
		jsonParser = new JSONParser();
	}

	/**
	 * function make Login Request
	 * 
	 * @param email
	 * @param password
	 * */
	public JSONObject loginUser(String email, String password) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.makeHttpRequest(URL, "GET", params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}

	/**
	 * function add new user
	 * 
	 * @param nif
	 * @param name
	 * @param surname
	 * @param province
	 * @param fec
	 * @param email
	 * @param password
	 * */
	public JSONObject newUser(String nif, String name, String surname,
			String province, String fec, String email, String password) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("nif", nif));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("surname", surname));
		params.add(new BasicNameValuePair("province", province));
		params.add(new BasicNameValuePair("date_born", fec));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
		return json;
	}

	/**
	 * function return causes
	 * 
	 * */
	public JSONObject causes() {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", list_cause));
		JSONObject json = jsonParser.makeHttpRequest(URL, "GET", params);

		return json;
	}

	/**
	 * function load user causes
	 * 
	 * @param idUser
	 * */
	public JSONObject mycauses(String idUser) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", list_mycause));
		params.add(new BasicNameValuePair("idUser", idUser));
		JSONObject json = jsonParser.makeHttpRequest(URL, "GET", params);
		
		return json;
	}

	/**
	 * function load User to modify
	 * 
	 * @param idUser
	 * */
	public JSONObject loadUser(String idUser) {
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", load_user));
		params.add(new BasicNameValuePair("iduser", idUser));
		JSONObject json = jsonParser.makeHttpRequest(URL, "GET", params);
		
		return json;
	}

	/**
	 * function update User
	 * 
	 * @param id
	 * @param name
	 * @param surname
	 * @param province
	 * @param fec
	 * */
	public JSONObject updateUser(String id, String name, String surname,
			String province, String fec) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", update_tag));
		params.add(new BasicNameValuePair("iduser", id));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("surname", surname));
		params.add(new BasicNameValuePair("province", province));
		params.add(new BasicNameValuePair("date_born", fec));
		JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
		
		return json;
	}

	/**
	 * function drop firm
	 * 
	 * @param idUser
	 * @param idCause
	 * */
	public JSONObject dropfirm(String idUser, String idCause) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tag_dropfirm));
		params.add(new BasicNameValuePair("idUser", idUser));
		params.add(new BasicNameValuePair("idCause", idCause));
		JSONObject json = jsonParser.makeHttpRequest(URL, "GET", params);
		
		return json;
	}

	/**
	 * function validate firm
	 * 
	 * @param idUser
	 * @param idCause
	 * */
	public JSONObject validatefirm(String idUser, String idCause) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tag_validafirm));
		params.add(new BasicNameValuePair("idUser", idUser));
		params.add(new BasicNameValuePair("idCause", idCause));
		JSONObject json = jsonParser.makeHttpRequest(URL, "GET", params);
		
		return json;
	}

	/**
	 * function load firm
	 * 
	 * @param idUser
	 * @param idCause
	 * @param firm
	 * */
	public JSONObject firm(String idUser, String idCause, String firm) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tag_firm));
		params.add(new BasicNameValuePair("idUser", idUser));
		params.add(new BasicNameValuePair("idCause", idCause));
		params.add(new BasicNameValuePair("firm", firm));
		JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
		
		return json;
	}
}
