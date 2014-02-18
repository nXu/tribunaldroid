package hu.nxu.tribunaldroid.networkComm;

import android.app.Activity;
import android.os.AsyncTask;

public class HttpGetTask extends AsyncTask<String, Byte, String> {
	protected Activity callingActivity;
	
	/**
	 * Initializes a new instance of the HttpGetTask class.
	 * @param callingActivity The instance of the calling activity.
	 */
	public HttpGetTask(Activity callingActivity)
	{
		this.callingActivity = callingActivity;
	}
	
	@Override
	/**
	 * Performs an asynchronous HTTP(S) GET request.
	 */
	protected String doInBackground(String... urls) {
		ConnectionManager cm = ConnectionManager.getInstance();
		
		try {
			return new String(cm.get(urls[0]), "UTF-8");
		} catch (Exception e) {
			return "#ERROR: " + e.getClass().getName();
		}
	}
	
}
