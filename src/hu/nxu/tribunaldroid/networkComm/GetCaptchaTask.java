package hu.nxu.tribunaldroid.networkComm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

public class GetCaptchaTask extends AsyncTask<String, Byte, byte[] >{
	private Activity callerActivity;
	private String[] messages;
	
	/**
	 * Initializes a new instance of the GetCaptchaTask class.
	 * @param activity Instance of the caller activity.
	 */
	public GetCaptchaTask(Activity activity) {
		this.callerActivity = activity;
		this.messages = this.callerActivity.getResources().getStringArray(this.callerActivity.getResources().getIdentifier("", "array", this.callerActivity.getPackageName()));
	}
	
	/**
	 * Gets a captcha asynchronously.
	 */
	@Override
	protected byte[] doInBackground(String... params) {
		ConnectionManager cm = ConnectionManager.getInstance();
		
		// Get a ReCaptcha challenge key
		String json;
		try {
			json = new String(cm.get("https://www.google.com/recaptcha/api/challenge?ajax=1&lang=en&k=" + params[0]), "UTF-8");
		} catch (Exception ex) {
			this.showErrorMessage(this.messages[Integer.parseInt(ex.getMessage())]);
			return null;
		}
		
		// Extract the challenge key
		Pattern pattern = Pattern.compile("challenge : '([a-zA-Z0-9\\-_]+)'");
		Matcher matcher = pattern.matcher(json);
		if (!matcher.matches()) {
			this.showErrorMessage(this.messages[3]);
			return null;
		}
		// Get the image from the challenge key
		
		
		return null;
	}

	/**
	 * Shows an error message in an alert dialog.
	 * @param message Message to show.
	 */
	protected void showErrorMessage(String message) {
		
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this.callerActivity);

		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setMessage(message)
		       .setTitle("Error")
		       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               dialog.cancel();
		           }
		       });
		// 3. Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
