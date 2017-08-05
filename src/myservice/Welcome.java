package myservice;

import com.opensymphony.xwork2.ActionSupport;

public class Welcome extends ActionSupport{
	
	public String run() {
		System.out.println("123");
		return SUCCESS;
	}
}
