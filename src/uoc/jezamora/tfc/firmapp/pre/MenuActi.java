/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Activity use for user menu
 */
package uoc.jezamora.tfc.firmapp.pre;

import uoc.jezamora.tfc.firmapp.R;

import uoc.jezamora.tfc.firmapp.ent.Session;
import uoc.jezamora.tfc.firmapp.mng.PrefsMngr;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;

public class MenuActi extends Activity implements OnClickListener {

	private Button btnCauses;

	private Button btnLogOut;
	private Button btnProfile;
	private Button btnDelete;
	private Button btnAddCause;
	private Button btnCondi;
	private Button btnMail;

	private Session session;
	private PrefsMngr myprefs = null;

	ProgressDialog myprogress;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dashboard_layout);

		this.myprefs = new PrefsMngr(getApplicationContext());

		Bundle b = getIntent().getExtras();
		session = (Session) b.getParcelable("session");

		btnProfile = (Button) findViewById(R.id.btnProfileMenu);
		btnCauses = (Button) findViewById(R.id.btnFirmMenu);
		btnLogOut = (Button) findViewById(R.id.btnLogout);
		btnDelete = (Button) findViewById(R.id.btnDownMenu);
		btnAddCause = (Button) findViewById(R.id.btnaddnewCause);
		btnCondi = (Button) findViewById(R.id.btnLaw);
		btnMail = (Button) findViewById(R.id.btnMailNew);

		btnProfile.setOnClickListener(this);
		btnCauses.setOnClickListener(this);
		btnLogOut.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnAddCause.setOnClickListener(this);
		btnCondi.setOnClickListener(this);
		btnMail.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		// Profile Menu
		case R.id.btnProfileMenu:

			Intent myIntent = new Intent(this, UserModActi.class);

			Bundle b = new Bundle();
			b.putParcelable("session", session);
			myIntent.putExtras(b);

			startActivity(myIntent);
			break;

		// Causes Menu
		case R.id.btnFirmMenu:

			Intent myIntentMenu = new Intent(this, CauseListActi.class);

			Bundle bM = new Bundle();
			bM.putParcelable("session", session);
			bM.putString("begin", "firm");

			myIntentMenu.putExtras(bM);
			startActivity(myIntentMenu);

			break;
		// add cause Menu
		case R.id.btnaddnewCause:
			final Intent myIntentLog1 = new Intent(this,
					RegisterCauseActi.class);
			startActivity(myIntentLog1);
			break;
		// Legal conditions Menu
		case R.id.btnLaw:
			final Intent myIntentLaw = new Intent(this, CondiActi.class);
			startActivity(myIntentLaw);
			break;
		// drop firm Menu
		case R.id.btnDownMenu:

			Intent myDownMenu = new Intent(this, CauseListActi.class);

			Bundle bMD = new Bundle();
			bMD.putParcelable("session", session);
			bMD.putString("begin", "mysigns");

			myDownMenu.putExtras(bMD);
			startActivity(myDownMenu);

			break;

		// Logout Menu
		case R.id.btnLogout:

			// clean preferences
			this.myprefs.setEmail("?");
			this.myprefs.setIdUser("?");
			this.myprefs.setName("?");
			this.myprefs.save();

			final Intent myIntentLogO = new Intent(this, LoginActi.class);

			AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
			alertbox.setMessage(this.getString(R.string.menu_logout));
			alertbox.setPositiveButton(this.getString(R.string.yes),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {

							startActivity(myIntentLogO);
							finish();
						}
					});
			alertbox.setNegativeButton(this.getString(R.string.no),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
						}
					});
			alertbox.show();

			break;
		// Profile Menu
		case R.id.btnMailNew:
			final Intent myIntentLog2 = new Intent(this,
					ContactActi.class);
			startActivity(myIntentLog2);
			break;

		}
	}
}
