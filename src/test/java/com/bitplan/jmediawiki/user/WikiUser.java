/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/WolfgangFahl/JMediawiki
 * and the license for JMediawiki applies
 * 
 */
package com.bitplan.jmediawiki.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Wiki User information
 * 
 * @author wf
 *
 */
public class WikiUser {

	/**
	 * Logging
	 */
	protected static java.util.logging.Logger LOGGER = java.util.logging.Logger
			.getLogger("com.bitplan.jmediawiki.user");

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
	 * @param email
	 *          the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * get input from standard in
	 * @param name
	 * @return
	 * @throws IOException 
	 */
	public static String getInput(String name) throws IOException {
		// prompt the user to enter the given name
		System.out.print("Please Enter " + name + ": ");

		// open up standard input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String value = br.readLine();
		return value;
	}

	/**
	 * get the Wiki user for the given wikiid
	 * 
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public static WikiUser getUser(String wikiid) {
		String user = System.getProperty("user.name");
		String userPropertiesFileName = System.getProperty("user.home") + "/.jmediawiki/"
				+ user + "_" + wikiid + ".ini";
		File propFile = new File(userPropertiesFileName);
		Properties props = new Properties();
		WikiUser result = new WikiUser();
		try {
			props.load(new FileReader(propFile));
			result.setUsername(props.getProperty("user"));
			result.setEmail(props.getProperty("email"));
			Crypt pcf = new Crypt(props.getProperty("cypher"),
					props.getProperty("salt"));
			result.setPassword(pcf.decrypt(props.getProperty("secret")));
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, "Please initialize the user ini file at "
					+ userPropertiesFileName);
			try {
				String username = getInput("username");
				String password = getInput("password");
				String email=getInput("email");
				String remember= getInput("shall i store "+username+"'s credentials encrypted in "+userPropertiesFileName+" y/n?");
				if (remember.trim().toLowerCase().startsWith("y")) {
					Crypt lCrypt=Crypt.getRandomCrypt();
					props.setProperty("cypher", lCrypt.getCypher());
					props.setProperty("salt", lCrypt.getSalt());
					props.setProperty("user", username);
					props.setProperty("email",email);
					props.setProperty("secret", lCrypt.encrypt(password));
					File userPropertiesFile=new File(userPropertiesFileName);
					if (!userPropertiesFile.getParentFile().exists()) {
						userPropertiesFile.getParentFile().mkdirs();
					}
					FileOutputStream propsFile=new FileOutputStream(userPropertiesFile);
					props.store(propsFile, "JMediawiki settings");
					propsFile.close();
				}
				result.setUsername(username);
				result.setPassword(password);
			} catch (IOException e1) {
				LOGGER.log(Level.SEVERE,e1.getMessage());
			} catch (GeneralSecurityException e1) {
				LOGGER.log(Level.SEVERE,e1.getMessage());
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		} catch (GeneralSecurityException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return result;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *          the username to set
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
	 * @param password
	 *          the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
