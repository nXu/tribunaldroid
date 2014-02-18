package hu.nxu.tribunaldroid.networkComm;

public class Cookie {
	public String Name;
	public String Value;
	
	Cookie(String name, String value)
	{
		this.Name = name;
		this.Value = value;
	}
	
	public String toString()
	{
		return this.Name + "=" + this.Value + "; ";
	}
}
