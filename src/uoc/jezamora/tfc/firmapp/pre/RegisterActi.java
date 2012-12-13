/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Activity use to register users
 */
package uoc.jezamora.tfc.firmapp.pre;

import java.util.Calendar;

import uoc.jezamora.tfc.firmapp.R;


import uoc.jezamora.tfc.firmapp.ent.Session;
import uoc.jezamora.tfc.firmapp.ent.User;
import uoc.jezamora.tfc.firmapp.mng.PrefsMngr;
import uoc.jezamora.tfc.firmapp.mng.UserMngr;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterActi extends Activity implements OnClickListener {

	private static String ST_OK = "1";
	private EditText txtUserN;
	private EditText txtSurN;
	private EditText txtNif;
	private EditText txtProv;
	private EditText txtEmail;
	private EditText txtPwd;
	private EditText txtPwd2;
	private EditText mDateDisplay;

	private Button btnReg;
	private ImageView btnCalendar;
	// rows to get date
	private int mYear;
	private int mMonth;
	private int mDay;
	static final int DATE_DIALOG_ID = 0;

	private Session session;
	private User user;
	private UserMngr userMngr;
	private PrefsMngr myprefs = null;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.myprefs = new PrefsMngr(getApplicationContext());

		setContentView(R.layout.activity_reg);

		iniView();

		btnReg.setOnClickListener(this);
		btnCalendar.setOnClickListener(this);
		mDateDisplay.setEnabled(false);

		// get data for calendar
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		context = this;

	}

	/*
	 * initialize activitys for views
	 */
	private void iniView() {
		btnReg = (Button) findViewById(R.id.btnAdd);
		btnCalendar = (ImageView) findViewById(R.id.btnCalendar);

		txtUserN = (EditText) findViewById(R.id.txtUserNameReg);
		txtSurN = (EditText) findViewById(R.id.txtSurnameReg);
		txtNif = (EditText) findViewById(R.id.txtNifReg);
		txtProv = (EditText) findViewById(R.id.txtProvinceReg);
		txtEmail = (EditText) findViewById(R.id.txtEmailReg);
		txtPwd = (EditText) findViewById(R.id.txtPassReg);
		txtPwd2 = (EditText) findViewById(R.id.txtPass2Reg);

		mDateDisplay = (EditText) findViewById(R.id.txtFecNacReg);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// Login
		case R.id.btnAdd:

			String name = txtUserN.getText().toString().trim();
			String surname = txtSurN.getText().toString().trim();
			String nif = txtNif.getText().toString().trim();
			String prov = txtProv.getText().toString().trim();
			String fec = mDateDisplay.getText().toString().trim();
			String email = txtEmail.getText().toString().trim();
			String pass = txtPwd.getText().toString().trim();
			String pass2 = txtPwd2.getText().toString().trim();

			if (pass.equals(pass2)) {

				user = new User(0, nif, name, surname, prov, fec, email, pass);

				userMngr = new UserMngr(user, context);
				String res = userMngr.validateUserData(true);
				if (res == null) {
					session = userMngr.newUser(user);

					if (session.getState().equals(ST_OK)) {
						this.myprefs.setEmail(session.getemail().toString()
								.trim());
						this.myprefs.setName(session.getname().toString()
								.trim());
						this.myprefs.setIdUser(String.valueOf(session
								.getUserId()));
						this.myprefs.save();

						Intent myIntent = new Intent(context, MenuActi.class);

						Bundle b = new Bundle();
						b.putParcelable("session", session);
						myIntent.putExtras(b);

						startActivity(myIntent);
						finish();

					} else {
						mostraErr(session.getDesc());
					}
				} else {
					mostraErr(res);
				}
			} else {
				mostraErr(this.getString(R.string.dif_pass));
			}
			;
			// ME FALTA AÑADIR LA VALIDACIÓN DEL DNI CORRECTO
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
	 * format data
	 */
	private void updateDisplay() {

		mDateDisplay.setText(new StringBuilder().append(mDay).append("-")
				.append(mMonth + 1).append("-").append(mYear).append(""));

	}

	/*
	 * get user data from the calendar
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
