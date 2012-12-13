/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Activity use to login
 */
package uoc.jezamora.tfc.firmapp.pre;

import uoc.jezamora.tfc.firmapp.R;
import uoc.jezamora.tfc.firmapp.ent.Login;
import uoc.jezamora.tfc.firmapp.ent.Session;
import uoc.jezamora.tfc.firmapp.mng.LoginMngr;
import uoc.jezamora.tfc.firmapp.mng.PrefsMngr;
import uoc.jezamora.tfc.firmapp.utils.AlertDialogManager;
import uoc.jezamora.tfc.firmapp.utils.ConnectionDetector;
import android.os.Bundle;
import android.app.Activity;
//import android.app.AlertDialog;
import android.content.Context;
//import android.content.DialogInterface;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActi extends Activity implements OnClickListener {

	private EditText txtUser;
	private EditText txtPwd;

	private LoginMngr loginMgr;
	private Login login;
	private Button btnLogin;
	private TextView link;

	private Context context;
	private Session session;
	private PrefsMngr myprefs = null;

	Boolean isInternetPresent = false;

	// Internet Connection detector
	private ConnectionDetector cd;

	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	private static String ST_OK = "1";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.myprefs = new PrefsMngr(getApplicationContext());

		setContentView(R.layout.activity_login);

		txtUser = (EditText) findViewById(R.id.txtLogUser);
		txtPwd = (EditText) findViewById(R.id.txtLogPass);
		link = (TextView) findViewById(R.id.label_usrname);

		// stress link text
		SpannableString content = new SpannableString(link.getText());
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		link.setText(content);

		btnLogin = (Button) findViewById(R.id.btnLogin);

		btnLogin.setOnClickListener(this);
		link.setOnClickListener(this);

		context = this;

	}

	public void onDestroy() {
		super.onDestroy();

	}

	public void onResume() {
		super.onRestart();

	}

	public void onClick(View v) {

		switch (v.getId()) {
		// Login
		case R.id.btnLogin:

			cd = new ConnectionDetector(getApplicationContext());

			isInternetPresent = cd.isConnectingToInternet();

			// Check if Internet present
			if (!isInternetPresent) {
				// Internet Connection is not present
				alert.showAlertDialog(LoginActi.this,
						LoginActi.this.getString(R.string.net_tit),
						LoginActi.this.getString(R.string.net_des), false);
			} else {

				String usr = txtUser.getText().toString().trim();
				String pwd = txtPwd.getText().toString().trim();

				login = new Login(usr, pwd, "");
				loginMgr = new LoginMngr(context);
				String valida = loginMgr.validaUsuari(login);
				if (valida == null) {

					session = loginMgr.Login(login);

					if (session.getState().equals(ST_OK)) {

						btnLogin.setBackgroundColor(22222);

						myprefs.setIdUser(String.valueOf(session.getUserId()));
						myprefs.setEmail(session.getemail());
						myprefs.setName(session.getname());
						myprefs.save();

						Intent myIntent = new Intent(context, MenuActi.class);

						Bundle b = new Bundle();
						b.putParcelable("session", session);
						myIntent.putExtras(b);

						startActivity(myIntent);
						finish();
						txtUser.setText("");
						txtPwd.setText("");
						;

					} else {
						alert.showAlertDialog(LoginActi.this,
								LoginActi.this.getString(R.string.tit_incpass),
								LoginActi.this.getString(R.string.des_incpass),
								false);

					}

				} else {
					alert.showAlertDialog(LoginActi.this,
							LoginActi.this.getString(R.string.tit_voidpass),
							LoginActi.this.getString(R.string.des_voidpass),
							false);

				}
			}
			break;

		// Register
		case R.id.label_usrname:
			cd = new ConnectionDetector(getApplicationContext());

			// Check if Internet present
			if (!cd.isConnectingToInternet()) {
				// Internet Connection is not present
				alert.showAlertDialog(LoginActi.this,
						LoginActi.this.getString(R.string.net_tit),
						LoginActi.this.getString(R.string.net_des), false);
				// stop executing code by return
				return;
			} else {
				// acces register activity
				Intent myIntent = new Intent(this, RegisterActi.class);

				Bundle b = new Bundle();
				b.putParcelable("session", session);
				myIntent.putExtras(b);
				startActivity(myIntent);
				finish();

			}
			break;
		}

	}
}
