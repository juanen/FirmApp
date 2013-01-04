/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Activity use to register causes
 */
package uoc.jezamora.tfc.firmapp.pre;

import java.util.Calendar;

import uoc.jezamora.tfc.firmapp.R;

import uoc.jezamora.tfc.firmapp.ent.Cause;
import uoc.jezamora.tfc.firmapp.ent.Session;

import uoc.jezamora.tfc.firmapp.mng.CauseMngr;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.content.DialogInterface;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterCauseActi extends Activity implements OnClickListener {

	private EditText txtName;
	private EditText txtDesc;
	private EditText mDateDisplayB;
	private EditText txtTotFirm;
	private EditText mDateDisplayE;

	private Button btnAdd;
	private ImageView btnCalendar;
	private int mYear;
	private int mMonth;
	private int mDay;
	private ImageView btnCalendarE;

	static final int DATE_DIALOG_ID = 0;

	private Session session;
	private Cause cause;
	private CauseMngr causeMngr;
	public boolean date;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_regcause);

		iniView();

		btnAdd.setOnClickListener(this);
		btnCalendar.setOnClickListener(this);
		btnCalendarE.setOnClickListener(this);

		//get data for the calendar
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

	}

	/*
	 * initialize activitys for views
	 */
	private void iniView() {
		btnAdd = (Button) findViewById(R.id.btnaddnewCause2);
		btnCalendar = (ImageView) findViewById(R.id.btnCalendarB);
		btnCalendarE = (ImageView) findViewById(R.id.btnCalendarE);

		txtName = (EditText) findViewById(R.id.txtCauseName);
		txtDesc = (EditText) findViewById(R.id.txtDescCause);
		txtTotFirm = (EditText) findViewById(R.id.txtTotFirm);

		mDateDisplayB = (EditText) findViewById(R.id.txtFecEnd);
		mDateDisplayE = (EditText) findViewById(R.id.txtFecBegin);
		
		mDateDisplayB.setEnabled(false);
		mDateDisplayE.setEnabled(false);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// add cause
		case R.id.btnaddnewCause2:

			String name = txtName.getText().toString().trim();
			String desc = txtDesc.getText().toString().trim();
			String fecB = mDateDisplayB.getText().toString().trim();
			String fecE = mDateDisplayE.getText().toString().trim();
			String Tot = txtTotFirm.getText().toString().trim();

			if (valRegs(name, desc, fecB, fecE, Tot)) {

				cause = new Cause(0, name, desc, fecB, fecE, Tot, "0");

				causeMngr = new CauseMngr();
				String res = causeMngr.newCause(cause);
				if (res == null) {

					AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
					alertbox.setMessage(this.getString(R.string.msg_newc));
					alertbox.setPositiveButton(this.getString(R.string.accept),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0,
										int arg1) {
									finish();
								}
							});

					alertbox.show();

				} else {
					mostraErr(session.getDesc());
				}
			} else {

				mostraErr("Por favor, verifique que ha cumplimentado todos los campos y vuelva a intentarlo");
			}

			break;
		case R.id.btnCalendarB:
			date = true;
			showDialog(DATE_DIALOG_ID);
			break;
		case R.id.btnCalendarE:
			date = false;
			showDialog(DATE_DIALOG_ID);
			break;
		}

	}

	public boolean valRegs(String name, String desc, String fecB, String fecE,
			String tot) {
		if (name.compareTo("") == 0 || desc.compareTo("") == 0
				|| fecB.compareTo("") == 0 || fecE.compareTo("") == 0
				|| tot.compareTo("") == 0) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * format data
	 */
	private void updateDisplay() {

		if (date) {
			mDateDisplayE.setText(new StringBuilder().append(mDay).append("-")
					.append(mMonth + 1).append("-").append(mYear).append(""));
		} else {
			mDateDisplayB.setText(new StringBuilder().append(mDay).append("-")
					.append(mMonth + 1).append("-").append(mYear).append(""));

		}

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
