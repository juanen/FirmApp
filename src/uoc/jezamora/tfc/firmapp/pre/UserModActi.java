/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Activity to modify user data
 */
package uoc.jezamora.tfc.firmapp.pre;

import java.util.Calendar;

import uoc.jezamora.tfc.firmapp.R;

import uoc.jezamora.tfc.firmapp.ent.Session;
import uoc.jezamora.tfc.firmapp.ent.User;
import uoc.jezamora.tfc.firmapp.mng.UserMngr;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

public class UserModActi extends Activity implements OnClickListener {

	private EditText txtUserN;
	private EditText txtSurN;
	private EditText txtNif;
	private EditText txtProv;
	private EditText txtEmail;

	private EditText mDateDisplay;

	private Button btnSave;
	private ImageView btnCalendar;
	private int mYear;
	private int mMonth;
	private int mDay;

	static final int DATE_DIALOG_ID = 0;

	private Session session;
	private User user;
	private UserMngr userMngr;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_mod);

		iniView();
		loadData();
		setData();

		btnSave.setOnClickListener(this);
		btnCalendar.setOnClickListener(this);
		mDateDisplay.setEnabled(false);

		// get date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		context = this;

	}

	private void loadData() {
		Bundle b = getIntent().getExtras();
		session = (Session) b.getParcelable("session");

		user = new User(0, "", "", "", "", "", "", "");

		userMngr = new UserMngr(user, context);

		user = userMngr.loadUser(String.valueOf(session.getUserId()));

	}

	private void setData() {
		txtUserN.setText(user.getuserName());
		txtSurN.setText(user.getSurname());
		txtNif.setText(user.getnif());
		txtProv.setText(user.getProvince());
		mDateDisplay.setText(user.getFec());
		txtEmail.setText(user.geteMail());
		txtEmail.setEnabled(false);
		txtNif.setEnabled(false);

	}

	/*
	 * initialize views
	 */
	private void iniView() {
		btnSave = (Button) findViewById(R.id.btnSave);
		btnCalendar = (ImageView) findViewById(R.id.btnCalendar);

		txtUserN = (EditText) findViewById(R.id.txtUserNameReg);
		txtSurN = (EditText) findViewById(R.id.txtSurnameReg);

		txtNif = (EditText) findViewById(R.id.txtNifReg);
		txtEmail = (EditText) findViewById(R.id.txtEmailReg);

		txtProv = (EditText) findViewById(R.id.txtProvinceReg);

		mDateDisplay = (EditText) findViewById(R.id.txtFecNacReg);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// save data
		case R.id.btnSave:
			AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
			alertbox.setMessage(this.getString(R.string.msg_modi));
			alertbox.setPositiveButton(this.getString(R.string.accept),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {

							String name = txtUserN.getText().toString().trim();
							String surname = txtSurN.getText().toString()
									.trim();

							String prov = txtProv.getText().toString().trim();
							String fec = mDateDisplay.getText().toString()
									.trim();

							user = new User(session.getUserId(), "", name,
									surname, prov, fec, "", "");

							userMngr = new UserMngr(user, context);
							String res = userMngr.validateModiUser(true);

							if (res == null) {

								String modi = userMngr.updateUser(user);
								if (modi.equals("1")) {
									finish();
								} else {
									mostraErr(modi);
								}
							} else {
								mostraErr(res);
							}
						}
					});
			alertbox.setNegativeButton(this.getString(R.string.cancel),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
						}
					});
			alertbox.show();
			break;
		case R.id.btnCalendar:
			showDialog(DATE_DIALOG_ID);
			break;
		}

	}

	public boolean valRegs(String name, String surname, String nif, String fec,
			String prov, String email, String pass, String pass2) {
		if (name.compareTo("") == 0 || surname.compareTo("") == 0
				|| nif.compareTo("") == 0 || fec.compareTo("") == 0
				|| email.compareTo("") == 0 || pass.compareTo("") == 0
				|| pass2.compareTo("") == 0 || pass2.compareTo("") == 0) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Format date
	 */
	private void updateDisplay() {

		mDateDisplay.setText(new StringBuilder().append(mDay).append("-")
				.append(mMonth + 1).append("-").append(mYear).append(""));

	}

	/*
	 * get user date
	 */
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;

			updateDisplay();

		}
	};

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	/*
	 * Show error
	 */
	private void mostraErr(String msg) {
		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		alertbox.setMessage(msg);
		alertbox.setNeutralButton(this.getString(R.string.accept),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						
					}
				});
		alertbox.show();
	}

}
