/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Activity show detail of selected cause
 */

package uoc.jezamora.tfc.firmapp.pre;

import uoc.jezamora.tfc.firmapp.R;

import uoc.jezamora.tfc.firmapp.ent.Cause;
import uoc.jezamora.tfc.firmapp.ent.Session;

import uoc.jezamora.tfc.firmapp.mng.UserCauseMngr;

import uoc.jezamora.tfc.firmapp.utils.AlertDialogManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.EditText;

public class CauseDetailActi extends Activity implements OnClickListener {

	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	Handler progresshandler;
	Message msg;

	private EditText txtCauseN;
	private EditText txtDescN;

	private Button btnFirm;
	private Button btnDel;

	// define linear layout to alternate the view of the activity
	private LinearLayout deletefirm;
	private LinearLayout addfirm;

	static final int DATE_DIALOG_ID = 0;

	private Session session;

	private Cause cause;

	private Context context;
	private String id;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_detcause);

		loadData();
		iniView();

		setData();

		// hide the keyboard
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		btnFirm.setOnClickListener(this);
		btnDel.setOnClickListener(this);
		context = this;
	}

	private void loadData() {

		Bundle b = getIntent().getExtras();

		session = (Session) b.getParcelable("session");

		cause = b.getParcelable("cause");

		id = b.getString("begin");

	}

	private void setData() {
		txtCauseN.setText(cause.getnameC());
		txtDescN.setText(cause.getdescC());

	}

	/*
	 * Validate views activity
	 */
	private void iniView() {

		deletefirm = (LinearLayout) findViewById(R.id.deletefirm);
		addfirm = (LinearLayout) findViewById(R.id.addfirm);
		btnFirm = (Button) findViewById(R.id.btnaddfirm);
		btnDel = (Button) findViewById(R.id.btndelfirm);

		if (id.equals("mysigns")) {
			addfirm.setVisibility(View.GONE);
			deletefirm.setVisibility(View.VISIBLE);

		} else {
			UserCauseMngr causeUser = new UserCauseMngr(context);
			Boolean valida2 = causeUser.validateFirm(
					String.valueOf(session.getUserId()),
					String.valueOf(cause.getidCause()));

			if (valida2) {
				deletefirm.setVisibility(View.GONE);
				addfirm.setVisibility(View.VISIBLE);
				alert.showAlertDialog(this, this.getString(R.string.remember),
						this.getString(R.string.firm_dup), false);
				btnFirm.setEnabled(false);

			}
		}

		txtCauseN = (EditText) findViewById(R.id.txtNameCause);
		txtDescN = (EditText) findViewById(R.id.txtDescCause);

		txtCauseN.setEnabled(false);
		txtDescN.setEnabled(false);

	}

	@SuppressWarnings("deprecation")
	public void onClick(View v) {
		switch (v.getId()) {
		// add user firm to the system
		case R.id.btnaddfirm:
			Intent myIntent = new Intent(context, FirmaActi.class);

			Bundle b = new Bundle();
			b.putParcelable("session", session);
			b.putParcelable("cause", cause);
			myIntent.putExtras(b);
			startActivity(myIntent);
			finish();

			break;
		// delete user firm from the system
		case R.id.btndelfirm:

			AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
			alertbox.setMessage(this.getString(R.string.drop_cause));
			alertbox.setPositiveButton(this.getString(R.string.yes),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {

							UserCauseMngr causeUser = new UserCauseMngr(context);
							Boolean valida = causeUser.dropFirm(
									String.valueOf(session.getUserId()),
									String.valueOf(cause.getidCause()));

							if (valida) {

								AlertDialog.Builder alertbox2 = new AlertDialog.Builder(
										context);
								alertbox2.setMessage(CauseDetailActi.this
										.getString(R.string.droped));
								alertbox2.setPositiveButton(
										CauseDetailActi.this
												.getString(R.string.accept),
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface arg0,
													int arg1) {

												finish();
											}
										});
								alertbox2.show();

							} else {
								alert.showAlertDialog(
										context,
										CauseDetailActi.this
												.getString(R.string.error),
										CauseDetailActi.this
												.getString(R.string.error_detcause),
										null);

							}
						}
					});
			alertbox.setNegativeButton(this.getString(R.string.no),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
						}
					});
			alertbox.show();

			break;
		case R.id.btnCalendar:
			showDialog(DATE_DIALOG_ID);
			finish();
			break;
		}

	}

}
