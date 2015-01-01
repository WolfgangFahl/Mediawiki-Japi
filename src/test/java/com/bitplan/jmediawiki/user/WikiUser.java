package com.bitplan.jmediawiki.user;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

/**
 * Wiki User information
 * @author wf
 *
 */
public class WikiUser {
	
	String username;
	String password;
	String email;
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * get the Wiki user for the given wikiid
	 * @return
	 * @throws Exception 
	 */
	public static WikiUser getUser(String wikiid) throws Exception {
		String user=System.getProperty("user.name");
		String userPropertiesFileName = System.getProperty("user.home")
				+ "/.Wiki/" + user + "_"+wikiid+".ini";
		File propFile = new File(userPropertiesFileName);
	  Properties props=new Properties();
	  props.load(new FileReader(propFile));
    WikiUser result=new WikiUser();
    result.setUsername(props.getProperty("user"));
    result.setEmail(props.getProperty("email"));
    Crypt pcf=new Crypt(props.getProperty("cypher"),props.getProperty("salt"));
    result.setPassword(pcf.decrypt(props.getProperty("secret")));
    return result;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
