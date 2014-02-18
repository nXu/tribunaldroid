package hu.nxu.tribunaldroid.networkComm;

import java.net.*;
import java.io.*;

public class ConnectionManager {
	private static final ConnectionManager _singleton = new ConnectionManager();
	
	private CookieCollection cookies;
	
	/**
	 * Initializes the only instance of the ConnectionManager class.
	 */
	private ConnectionManager()
	{
		this.cookies = new CookieCollection();
	}
	
	/**
	 * Gets the instance of the singleton ConnectionManager class.
	 * @return The instance of the ConnnectionManager class.
	 */
	public static ConnectionManager getInstance()
	{
		return _singleton;
	}
	
	/**
	 * Performs a HTTP(S) GET request.
	 * @param uri	The URI to connect to.
	 * @return		The response body as a byte array.
	 * @throws Exception
	 */
	public byte[] get(String uri) throws Exception
	{
		// Connect
		URL myUrl = new URL(uri);
		HttpURLConnection urlConn = (HttpURLConnection)myUrl.openConnection();
		
		// Set cookies and method
		urlConn.setRequestProperty("Cookie", this.cookies.toString());
		urlConn.setRequestMethod("GET");
		
		try
		{
			urlConn.connect();
		}
		catch (Exception c)
		{
			throw new Exception("01");
		}
		
		// Set the cookies
		this.setCookies(urlConn);
		
		// Read response
		return this.readResponseBody(urlConn);
	}
	
	/**
	 * Performs a HTTP(S) POST request.
	 * @param uri		The URI to connect to.
	 * @param postData	The post data.
	 * @return			The response body as byte array.
	 * @throws Exception
	 */
	public byte[] post(String uri, String postData) throws Exception
	{
		// Connect
		URL myUrl = new URL(uri);
		HttpURLConnection urlConn = (HttpURLConnection)myUrl.openConnection();
		
		// Set properties and method
		urlConn.setRequestMethod("POST");
		urlConn.setRequestProperty("Cookie", this.cookies.toString());
		urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0");
		urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		urlConn.setRequestProperty("Content-Length", Integer.toString(postData.length()));
		urlConn.setRequestProperty("Accept-Charset", "UTF-8");
		
		urlConn.setDoInput(true);
		urlConn.setDoOutput(true);
		
		try
		{
			OutputStream output = urlConn.getOutputStream();
			try {
			     output.write(postData.getBytes("UTF-8"));
			} finally {
			     try { 
			    	 output.close(); 
			     } 
			     catch (IOException ex) {
			    	 throw new Exception("02"); 
			     }
			}

		}
		catch (Exception c)
		{
			throw new Exception("01");
		}
		
		// Set the cookies
		this.setCookies(urlConn);
		
		// Read response
		return this.readResponseBody(urlConn);
	}
	
	/**
	 * Reads the body of a HTTP(S) response.
	 * @param urlConn	The opened connection.
	 * @throws Exception 
	 */
	private byte[] readResponseBody(URLConnection urlConn) throws Exception
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[512];
		BufferedInputStream input = new BufferedInputStream(urlConn.getInputStream());
		
		try
		{
			while (input.read(buffer) > -1) {
				output.write(buffer);
			}
		}
		catch (Exception ex)
		{
			throw new Exception("02");
		}
		
		// Flush output stream and return
		output.flush();
		return output.toByteArray();
	}
	
	/**
	 * Reads the cookies from a HTTP(S) response and updates the cookie list.
	 * @param urlConn The opened URL Connected, the response must be available.
	 * 
	 */
	private void setCookies(URLConnection urlConn)
	{
		String headerName = null;
		for (int i=1; (headerName = urlConn.getHeaderFieldKey(i))!=null; i++) {
		 	if (headerName.equals("Set-Cookie")) {                  
				String cookie = urlConn.getHeaderField(i);               
		        cookie = cookie.substring(0, cookie.indexOf(";"));
		        String cookieName = cookie.substring(0, cookie.indexOf("="));
		        String cookieValue = cookie.substring(cookie.indexOf("=") + 1, cookie.length());
		        
		        this.cookies.setCookie(new Cookie(cookieName, cookieValue));
		 	}
		}
	}
}
