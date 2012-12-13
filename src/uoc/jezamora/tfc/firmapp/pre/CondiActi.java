/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Activity show legal conditions of the app
 */
package uoc.jezamora.tfc.firmapp.pre;

import uoc.jezamora.tfc.firmapp.R;
import uoc.jezamora.tfc.firmapp.utils.AlertDialogManager;

import android.os.Bundle;
import android.app.Activity;

public class CondiActi extends Activity {
	AlertDialogManager alert = new AlertDialogManager();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_condi);
		alert.showAlertDialog(this, this.getString(R.string.tit_sorry),
				this.getString(R.string.des_sorry), true);

	}

}
