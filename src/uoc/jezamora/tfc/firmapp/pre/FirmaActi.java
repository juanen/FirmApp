/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Activity use to firm a cause
 */
package uoc.jezamora.tfc.firmapp.pre;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import uoc.jezamora.tfc.firmapp.R;
import uoc.jezamora.tfc.firmapp.ent.Cause;
import uoc.jezamora.tfc.firmapp.ent.Firm;
import uoc.jezamora.tfc.firmapp.ent.Session;
import uoc.jezamora.tfc.firmapp.mng.UserCauseMngr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class FirmaActi extends Activity {

	ProgressDialog myprogress;
	Handler progresshandler;
	Message msg;
	Cause cause = null;
	Session session = null;
	Context context;
	Firm firm;
	UserCauseMngr userCause;

	private closefirmView sc = null;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		Toast.makeText(this, this.getString(R.string.firm_screen),
				Toast.LENGTH_LONG).show();

		Log.i("CH18::FirmaActi", "onCreate not null");

		Log.i("CH18::FirmaActi", "bad bundle");

		Bundle b = getIntent().getExtras();

		session = (Session) b.getParcelable("session");

		cause = (Cause) b.getParcelable("cause");
		Log.i("CH18::FirmaActi", "from Bundle");
		context = this;

		this.sc = new closefirmView(this);
		setContentView(this.sc);

		if (this.session == null || this.cause == null) {
			// if we get here without a job, something went wrong!
			finish();
		}
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		openOptionsMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// add our menu options
		menu.add(0, 0, 0, this.getString(R.string.sign_send));
		menu.add(0, 1, 1, this.getString(R.string.cancel));
		menu.setGroupEnabled(0, true);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 0:
			try {

				this.myprogress = ProgressDialog.show(this, this.getString(R.string.close_firm),
						this.getString(R.string.net_firm), true, false);
				this.progresshandler = new Handler() {

					@Override
					public void handleMessage(Message msg) {
						
						switch (msg.what) {
						case 0:
							// update progress bar
							FirmaActi.this.myprogress.setMessage(""
									+ (String) msg.obj);
							break;
						case 1:
							FirmaActi.this.myprogress.cancel();
							finish();
							break;
						}
						super.handleMessage(msg);
					}

				};

				Thread workthread = new Thread(new DoFirmaActi());

				workthread.start();

			} catch (Exception e) {
				// tell user we failed
				Log.d("FirmaActi", e.getMessage());
				this.msg = new Message();
				this.msg.what = 1;
				this.progresshandler.sendMessage(this.msg);
			}
			return true;
		case 1:
			// bail
			finish();
			return true;
		}
		return false;
	}

	class DoFirmaActi implements Runnable {

		DoFirmaActi() {

		}

		public void run() {

			try {
				FileOutputStream os = getApplication().openFileOutput(
						"sig.jpg", 0);
				FirmaActi.this.sc.Save(os);
				os.flush();
				os.close();
				// reopen to so we can send this data to server
				File f = new File(getApplication().getFileStreamPath("sig.jpg")
						.toString());
				long flength = f.length();

				FileInputStream is = getApplication().openFileInput("sig.jpg");
				byte data[] = new byte[(int) flength];
				int count = is.read(data);
				if (count != (int) flength) {
					// bad read
				}
				FirmaActi.this.msg = new Message();
				FirmaActi.this.msg.what = 0;
				FirmaActi.this.msg.obj = FirmaActi.this.getString(R.string.net_server);

				FirmaActi.this.progresshandler.sendMessage(FirmaActi.this.msg);

				URL url = new URL(
						"http://juanza.comuv.com/closefirm.php?idUser="
								+ session.getUserId() + "&idCause="
								+ +cause.getidCause());
				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				BufferedOutputStream wr = new BufferedOutputStream(
						conn.getOutputStream());
				wr.write(data);
				wr.flush();
				wr.close();

				FirmaActi.this.msg = new Message();
				FirmaActi.this.msg.what = 0;
			
				FirmaActi.this.msg.obj = FirmaActi.this.getString(R.string.send_data);
				FirmaActi.this.progresshandler.sendMessage(FirmaActi.this.msg);

				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				String line = "";
				Boolean bSuccess = false;
				while ((line = rd.readLine()) != null) {
					if (line.indexOf("SUCCESS") != -1) {
						bSuccess = true;
					}
					// Process line...
					Log.v("FirmaActi", line);
				}
				wr.close();
				rd.close();

				if (bSuccess) {
					//ok

					FirmaActi.this.msg = new Message();
					FirmaActi.this.msg.what = 0;
					FirmaActi.this.msg.obj = FirmaActi.this.getString(R.string.reg_firm);
	
					FirmaActi.this.progresshandler
							.sendMessage(FirmaActi.this.msg);

					Intent myIntent = new Intent(context, ShareActi.class);

					Bundle b = new Bundle();

					b.putParcelable("cause", cause);
					myIntent.putExtras(b);

					startActivity(myIntent);

				} else {
					// failed
					FirmaActi.this.msg = new Message();
					FirmaActi.this.msg.what = 0;

					FirmaActi.this.msg.obj = FirmaActi.this.getString(R.string.err_closef);

					FirmaActi.this.progresshandler
							.sendMessage(FirmaActi.this.msg);

					
					FirmaActi.this.setResult(0);
				}
			} catch (Exception e) {
				Log.d("CH18",
						"Failed to submit job close signature: "
								+ e.getMessage());
			}
			FirmaActi.this.msg = new Message();
			FirmaActi.this.msg.what = 1;
			FirmaActi.this.progresshandler.sendMessage(FirmaActi.this.msg);
		}
	}

	public class closefirmView extends View {

		Bitmap _bitmap;
		Canvas _canvas;
		final Paint _paint;
		int lastX;
		int lastY;

		public closefirmView(Context c) {
			super(c);
			Log.i("CH18::FirmaActi", "closefirmView constructor");
			
			this._paint = new Paint();
			this._paint.setColor(Color.BLACK);
			this.lastX = -1;
		}

		public boolean Save(OutputStream os) {
			try {

				this._bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
				invalidate();
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		public void Reset() {
			// clear image in next versions
		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			openOptionsMenu();

			Log.i("CH18::FirmaActi", "closefirmView:onSizeChanged");
			Bitmap img = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas();
			canvas.setBitmap(img);

			if (this._bitmap != null) {
				canvas.drawBitmap(img, 0, 0, null);
			}
			this._bitmap = img;
			this._canvas = canvas;
			this._canvas.drawColor(Color.WHITE);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			if (this._bitmap != null) {
				canvas.drawBitmap(this._bitmap, 0, 0, null);
			}
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			openOptionsMenu();
			int action = event.getAction();

			int X = (int) event.getX();
			int Y = (int) event.getY();

			switch (action) {
			case MotionEvent.ACTION_UP:
				
				this.lastX = -1;
				break;

			case MotionEvent.ACTION_DOWN:
				if (this.lastX != -1) {
					if ((int) event.getX() != this.lastX) {
						this._canvas.drawLine(this.lastX, this.lastY, X, Y,
								this._paint);
					}
				}
				this.lastX = (int) event.getX();
				this.lastY = (int) event.getY();
				break;

			case MotionEvent.ACTION_MOVE:

				if (this.lastX != -1) {
					this._canvas.drawLine(this.lastX, this.lastY, X, Y,
							this._paint);
				}

				this.lastX = (int) event.getX();
				this.lastY = (int) event.getY();
				break;
			}
			
			invalidate();

			return true;
		}
	}

}
