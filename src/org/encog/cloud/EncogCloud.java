package org.encog.cloud;

import java.util.HashMap;
import java.util.Map;

public class EncogCloud {
	
	private String session;
	
	public void connect(String uid, String pwd)
	{
		CloudRequest request = new CloudRequest();
		Map<String,String> args = new HashMap<String,String>();
		args.put("uid", uid);
		args.put("pwd", pwd);
		request.post("login", args);
		if( !"success".equals(request.getStatus()))
		{
			throw new EncogCloudError(request.getMessage());
		}
		this.session = request.getSession();
	}
	
	public void logout()
	{
		CloudRequest request = new CloudRequest();
		request.processSessionGET(this.session,"logout");
		validateSession();
		if( isConnected() )
		{
			throw new EncogCloudError("Logout failed.");
		}
	}
	
	public void validateSession()
	{
		CloudRequest request = new CloudRequest();
		request.processSessionGET(this.session,"");
		if( !request.getStatus().equals("success"))
			this.session = null;
	}
	
	public boolean isConnected()
	{
		return session!=null;
	}
	
	public String getSession()
	{
		return session;
	}
	
	public static void main(String[] args)
	{
		EncogCloud cloud = new EncogCloud();
		cloud.connect("test", "test");
		cloud.validateSession();
		cloud.logout();
		System.out.println(cloud.getSession());
	}
}