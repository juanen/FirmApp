/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Activity use to select options from preferences
 */
package uoc.jezamora.tfc.firmapp.pre;

import uoc.jezamora.tfc.firmapp.R;

import uoc.jezamora.tfc.firmapp.ent.Session;
import uoc.jezamora.tfc.firmapp.mng.PrefsMngr;
import android.os.Bundle;
import android.app.Activity;

import android.content.Context;

import android.content.Intent;

public class SplashActi extends Activity {

	private Context context;
	private Session session;
	private PrefsMngr myprefs = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.myprefs = new PrefsMngr(getApplicationContext());

		setContentView(R.layout.splash);

		if (this.myprefs.getEmail().equals("?")
				|| this.myprefs.getName().equals("?")
				|| this.myprefs.getId().equals("?")) {

			startActivity(new Intent(getApplication(), LoginActi.class));
			finish();

		} else {

			context = this;
			Intent myIntent = new Intent(context, MenuActi.class);
			session = new Session(Integer.parseInt(myprefs.getId()),
					myprefs.getEmail(), myprefs.getName(), "desc", null);
			Bundle b = new Bundle();
			b.putParcelable("session", session);
			myIntent.putExtras(b);

			startActivity(myIntent);

			finish();
		}
	}

}
