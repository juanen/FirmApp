/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Activity show list view of activitys
 */
package uoc.jezamora.tfc.firmapp.pre;

import java.util.ArrayList;
import java.util.HashMap;

import uoc.jezamora.tfc.firmapp.R;

import uoc.jezamora.tfc.firmapp.ent.Cause;
import uoc.jezamora.tfc.firmapp.ent.Session;
import uoc.jezamora.tfc.firmapp.mng.CauseMngr;
import uoc.jezamora.tfc.firmapp.utils.AlertDialogManager;
import uoc.jezamora.tfc.firmapp.utils.ListAdapter;
import android.os.Bundle;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;

import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.ListView;

public class CauseListActi extends Activity {

	private CauseMngr causeMngr;

	private Context context;
	private Session session;
	private String idtxt;

	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	ArrayList<HashMap<String, String>> causes = new ArrayList<HashMap<String, String>>();

	ListView list;
	ListAdapter adapter;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_causes);
		Bundle b = getIntent().getExtras();
		session = (Session) b.getParcelable("session");
		
		context = this;

		idtxt = b.getString("begin");
		

		causeMngr = new CauseMngr();

		if (idtxt.equals("mysigns")) {

			causes = causeMngr.myCauses(String.valueOf(session.getUserId()));

			this.setTitle(CauseListActi.this
					.getString(R.string.tit_listfirm));

		} else {
			causes = causeMngr.Causes();
		}

		if (causes != null && causes.size() > 0) {
			list = (ListView) findViewById(R.id.list);
			adapter = new ListAdapter(this, causes);
			list.setAdapter(adapter);

			// Click event for single list row
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					HashMap<String, String> selec = causes.get(position);

					Cause cause_sel = new Cause(Integer.parseInt(selec
							.get("idCause").toString().trim()), selec
							.get("name").toString().trim(), selec
							.get("description").toString().trim(), selec
							.get("end").toString().trim(), selec.get("begin")
							.toString().trim(), selec.get("total_firm")
							.toString().trim(), selec.get("total_need")
							.toString().trim());

					Intent myIntent = new Intent(context, CauseDetailActi.class);

					Bundle b = new Bundle();
					b.putParcelable("session", session);
					b.putParcelable("cause", cause_sel);

					b.putString("begin", idtxt);
					myIntent.putExtras(b);
					startActivity(myIntent);
					finish();

				}
			});
		} else {

			AlertDialog.Builder alertbox2 = new AlertDialog.Builder(context);
			alertbox2
					.setMessage(CauseListActi.this
							.getString(R.string.tit_listvoid));
			alertbox2.setPositiveButton(CauseListActi.this
					.getString(R.string.accept),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {

							finish();
						}
					});
			alertbox2.show();

		}
	}

	public void onDestroy() {
		super.onDestroy();

	}

	public void onResume() {
		super.onRestart();

	}

}
