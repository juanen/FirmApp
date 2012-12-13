/**
 * @Author JE Zamora Alvarez - UOC - TFC
 * @Date 2012/12 - V1.0
 *
 * Activity use share causes
 */

package uoc.jezamora.tfc.firmapp.pre;

import java.io.IOException;
import java.net.MalformedURLException;

import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import twitter4j.Twitter;

import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import uoc.jezamora.tfc.firmapp.R;

import uoc.jezamora.tfc.firmapp.ent.Cause;
import uoc.jezamora.tfc.firmapp.utils.AlertDialogManager;
import uoc.jezamora.tfc.firmapp.utils.ConnectionDetector;
import uoc.jezamora.tfc.firmapp.utils.FacebookConnector;
import uoc.jezamora.tfc.firmapp.utils.SessionEvents;

import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Intent;

import android.net.Uri;

import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ShareActi extends Activity {

	// Twitter oauth urls
	private static final String CONSUMER_KEY = "PW9wVdEPSjFJJo3ILYqig";
	private static final String CONSUMER_SECRET = "Z3LEhHVeICNtLTpSdaVaslyitk49NpjSTNzGf5PVy4";
	private static final String REQUEST_URL = "https://api.twitter.com/oauth/request_token";
	private static final String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";
	private static final String ACCESS_URL = "https://api.twitter.com/oauth/access_token";
	private static final String CALLBACK_URL = "app://ShareActi";

	// facebook
	private static final String FACEBOOK_APPID = "119263394902248";
	private static final String FACEBOOK_PERMISSION = "publish_stream";
	private static final String TAG = "FacebookSample";

	private Button btnFacebook;
	private Button btnTwitter;
	private AccessToken accessToken;
	private Cause cause;

	private CommonsHttpOAuthConsumer httpOauthConsumer;
	private OAuthProvider httpOauthprovider;

	private final Handler mFacebookHandler = new Handler();
	private final Handler mTwitterHandler = new Handler();

	private FacebookConnector facebookConnector;

	// Progress dialog
	ProgressDialog pDialog;

	// Internet Connection detector
	private ConnectionDetector cd;

	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	final Runnable mUpdateFacebookNotification = new Runnable() {
		public void run() {
			Toast.makeText(getBaseContext(),
					ShareActi.this.getString(R.string.face_updated),
					Toast.LENGTH_LONG).show();
		}
	};

	final Runnable mUpdateTwitterNotification = new Runnable() {
		public void run() {
			Toast.makeText(getBaseContext(),
					ShareActi.this.getString(R.string.twit_updated),
					Toast.LENGTH_LONG).show();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.icoshare_layout);

		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(ShareActi.this,
					ShareActi.this.getString(R.string.net_tit),
					ShareActi.this.getString(R.string.net_des), false);
			// stop executing code by return
			return;
		}

		// Check if twitter keys are set
		if (CONSUMER_KEY.trim().length() == 0
				|| CONSUMER_SECRET.trim().length() == 0) {
			// Internet Connection is not present
			alert.showAlertDialog(ShareActi.this, "Twitter oAuth tokens",
					"Please set your twitter oauth tokens first!", false);
			// stop executing code by return
			return;
		}

		btnFacebook = (Button) findViewById(R.id.btnFacebook);
		btnTwitter = (Button) findViewById(R.id.btnTwitter);
		Bundle b = getIntent().getExtras();

		cause = (Cause) b.getParcelable("cause");

		this.facebookConnector = new FacebookConnector(FACEBOOK_APPID, this,
				getApplicationContext(), new String[] { FACEBOOK_PERMISSION });

		btnFacebook.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				autorice_Face();
				btnFacebook.setEnabled(false);
			}

		});

		btnTwitter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				autorice();

			}

		});

	}

	// login on twitter
	private void autorice() {

		try {
			httpOauthConsumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,
					CONSUMER_SECRET);
			httpOauthprovider = new DefaultOAuthProvider(REQUEST_URL,
					ACCESS_URL, AUTHORIZE_URL);
			String authUrl = httpOauthprovider.retrieveRequestToken(
					httpOauthConsumer, CALLBACK_URL);

			this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse(authUrl)));

		} catch (Exception e) {
			e.getMessage();
		}

	}

	// function to login with facebook
	public void autorice_Face() {
		clearCredentials();

		if (facebookConnector.getFacebook().isSessionValid()) {
			postMessageInThread();
		} else {
			SessionEvents.AuthListener listener = new SessionEvents.AuthListener() {

				@Override
				public void onAuthSucceed() {
					postMessageInThread();
				}

				@Override
				public void onAuthFail(String error) {

				}
			};
			SessionEvents.addAuthListener(listener);
			facebookConnector.login();
		}

	}

	// function to post a message in facebook
	private void postMessageInThread() {
		Thread t = new Thread() {
			public void run() {

				try {
					facebookConnector.postMessageOnWall(getFacebookMsg());
					mFacebookHandler.post(mUpdateFacebookNotification);
					// logout from facebook
					clearCredentials();
				} catch (Exception ex) {
					Log.e(TAG, "Error sending msg", ex);
				}
			}
		};
		t.start();
	}

	private String getFacebookMsg() {
		return cause.getnameC() + ShareActi.this.getString(R.string.msg_share);
	}

	// function to recive response of twitter
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		Uri uri = intent.getData();
		if (uri != null && uri.toString().startsWith(CALLBACK_URL)) {
			String verifier = uri
					.getQueryParameter(oauth.signpost.OAuth.OAUTH_VERIFIER);
			try {
				httpOauthprovider.retrieveAccessToken(httpOauthConsumer,
						verifier);
				accessToken = new AccessToken(httpOauthConsumer.getToken(),
						httpOauthConsumer.getTokenSecret());

				enviaTweet();
				mTwitterHandler.post(mUpdateTwitterNotification);
				btnTwitter.setEnabled(false);

			} catch (Exception e) {

			}
		}
	}

	// send tweet
	private void enviaTweet() {
		try {
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
			twitter.setOAuthAccessToken(accessToken);

			if (cause.getnameC().length() > 90) {
				twitter.updateStatus(cause.getnameC().substring(0, 90) + ".."
						+ ShareActi.this.getString(R.string.msg_share));
			} else {
				twitter.updateStatus(cause.getnameC()
						+ ShareActi.this.getString(R.string.msg_share));
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	// clear facebook credentials
	public void clearCredentials() {
		try {
			facebookConnector.getFacebook().logout(getApplicationContext());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
