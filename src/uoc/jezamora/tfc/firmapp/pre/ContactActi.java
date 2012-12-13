/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Activity use to contact with the admin
 */
package uoc.jezamora.tfc.firmapp.pre;

import uoc.jezamora.tfc.firmapp.R;
import uoc.jezamora.tfc.firmapp.utils.AlertDialogManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ContactActi extends Activity {

	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	EditText etEmail;
	EditText etSubject;
	EditText etBody;

	TextView etDesti;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);

		Button btnSend = (Button) findViewById(R.id.btnSend);

		/* get data send */
		etDesti = (TextView) findViewById(R.id.txtForEmail);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etSubject = (EditText) findViewById(R.id.etSubject);
		etBody = (EditText) findViewById(R.id.etBody);
		etEmail.setText(this.getString(R.string.admin_email));
		etEmail.setVisibility(View.GONE);
		etDesti.setVisibility(View.GONE);

		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String res = validateUserData(true);
				if (res == null) {

					Intent itSend = new Intent(
							android.content.Intent.ACTION_SEND);

					itSend.setType("plain/text");

					/* put data to send */
					itSend.putExtra(android.content.Intent.EXTRA_EMAIL,
							new String[] { etEmail.getText().toString() });
					itSend.putExtra(android.content.Intent.EXTRA_SUBJECT,
							etSubject.getText().toString());
					itSend.putExtra(android.content.Intent.EXTRA_TEXT,
							etBody.getText());

					/* initialize activity send */
					startActivity(itSend);
					
				} else {

					alert.showAlertDialog(ContactActi.this,
							ContactActi.this.getString(R.string.error), res,
							false);
				}

			}
		});
	}

	/*
	 * Validate data
	 */
	public String validateUserData(boolean pwdCheck) {

		if (!etEmail.getText().toString()
				.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
			return this.getString(R.string.err_email);
		}
		if (etSubject.getText().toString().compareTo("") == 0) {
			return this.getString(R.string.err_subject);
		}
		if (etBody.getText().toString().compareTo("") == 0) {
			return this.getString(R.string.err_body);
		} else
			return null;
	}
}
