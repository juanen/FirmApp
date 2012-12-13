/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * class to format/setting array of listview
 */
package uoc.jezamora.tfc.firmapp.utils;

import java.util.ArrayList;
import java.util.HashMap;

import uoc.jezamora.tfc.firmapp.R;
import uoc.jezamora.tfc.firmapp.mng.CauseMngr;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public ListAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.list_item, null);

		TextView nameCause = (TextView) vi.findViewById(R.id.causeList); // nameCause

		HashMap<String, String> cause = new HashMap<String, String>();
		cause = data.get(position);

		// Setting all values in listview
		if (cause.get(CauseMngr.KEY_NAME) != null) {
			nameCause.setText(cause.get(CauseMngr.KEY_NAME));

		}
		return vi;
	}
}