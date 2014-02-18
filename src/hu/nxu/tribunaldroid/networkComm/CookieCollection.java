package hu.nxu.tribunaldroid.networkComm;

import java.util.*;

public class CookieCollection {
	private List<Cookie> cookieList;
	
	CookieCollection()
	{
		this.cookieList = new ArrayList<Cookie>();
	}
	
	/**
	 * Creates or updates a cookie.
	 * @param cookie	Cookie to add or update.
	 */
	public void setCookie(Cookie cookie)
	{
		for(int i = 0; i < this.cookieList.size(); ++i) {
			if (this.cookieList.get(i).Name == cookie.Name) {
				this.cookieList.get(i).Value = cookie.Value;
				return;
			}
		}
		
		this.cookieList.add(cookie);
	}
	
	public String toString()
	{
		String retVal = "";
		
		for(Cookie cookie : this.cookieList) {
			retVal += cookie.toString();
		}
		
		return retVal;
	}
}
