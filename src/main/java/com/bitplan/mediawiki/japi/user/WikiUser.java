/**
 *
 * This file is part of the https://github.com/WolfgangFahl/Mediawiki-Japi open source project
 *
 * Copyright 2015-2019 BITPlan GmbH https://github.com/BITPlan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *  You may obtain a copy of the License at
 *
 *  http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bitplan.mediawiki.japi.user;

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

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

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
      .getLogger("com.bitplan.mediawiki.japi.user");

  @Option(name = "-u", aliases = { "--user" }, usage = "username")
  String username;
  @Option(name = "-p", aliases = { "--password" }, usage = "password")
  String password;
  @Option(name = "-e", aliases = { "--email" }, usage = "email")
  String email;
  @Option(name = "-w", aliases = { "--wikiId" }, usage = "wiki id")
  String wikiid;
  @Option(name = "-y", aliases = { "--store" }, usage = "store without asking")
  boolean yes = false;

  private CmdLineParser parser;

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
   * 
   * @param name
   * @param br
   *          - the buffered reader to read from
   * @return the input returned
   * @throws IOException
   */
  public static String getInput(String name, BufferedReader br)
      throws IOException {
    // prompt the user to enter the given name
    System.out.print("Please Enter " + name + ": ");

    String value = br.readLine();
    return value;
  }

  /**
   * get the property file for the given wikiId and user
   * 
   * @param wikiId
   *          - the wiki to get the data for
   * @param user
   *          - the user
   * @return the property File
   */
  public static File getPropertyFile(String wikiId, String user) {
    String userPropertiesFileName = System.getProperty("user.home")
        + "/.mediawiki-japi/" + user + "_" + wikiId + ".ini";
    File propFile = new File(userPropertiesFileName);
    return propFile;
  }

  /**
   * get the propertyFile for the given wikiId
   * 
   * @param wikiId
   * @return the propertyFile
   */
  public static File getPropertyFile(String wikiId) {
    String user = System.getProperty("user.name");
    return getPropertyFile(wikiId, user);
  }

  /**
   * get the Properties for the given wikiId
   * 
   * @param wikiId
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   */
  public static Properties getProperties(String wikiId)
      throws FileNotFoundException, IOException {
    File propFile = getPropertyFile(wikiId);
    Properties props = new Properties();
    props.load(new FileReader(propFile));
    return props;
  }

  /**
   * get the Wiki user for the given wikiid
   * 
   * @param wikiId
   *          - the id of the wiki
   * @param siteurl
   *          - the siteurl
   * @return a Wikiuser for this site
   */
  public static WikiUser getUser(String wikiId, String siteurl) {

    WikiUser result = null;
    try {
      Properties props = getProperties(wikiId);
      result = new WikiUser();
      result.setUsername(props.getProperty("user"));
      result.setEmail(props.getProperty("email"));
      Crypt pcf = new Crypt(props.getProperty("cypher"),
          props.getProperty("salt"));
      result.setPassword(pcf.decrypt(props.getProperty("secret")));
    } catch (FileNotFoundException e) {
      String msg = help(wikiId, siteurl);
      LOGGER.log(Level.SEVERE, msg);
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

  /**
   * create a credentials ini file from the command line
   */
  public void createIniFile(String... args) {
    try {
      parser = new CmdLineParser(this);
      parser.parseArgument(args);
      // open up standard input
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      if (wikiid == null)
        wikiid = getInput("wiki id", br);
      if (username == null)
        username = getInput("username", br);
      if (password == null)
        password = getInput("password", br);
      if (email == null)
        email = getInput("email", br);
      File propFile = getPropertyFile(wikiid, username);
      if (!yes) {
        String remember = getInput("shall i store " + username
            + "'s credentials encrypted in " + propFile.getName() + " y/n?",
            br);
        yes = remember.trim().toLowerCase().startsWith("y");
      }
      if (yes) {
        Crypt lCrypt = Crypt.getRandomCrypt();
        Properties props = new Properties();
        props.setProperty("cypher", lCrypt.getCypher());
        props.setProperty("salt", lCrypt.getSalt());
        props.setProperty("user", username);
        props.setProperty("email", email);
        props.setProperty("secret", lCrypt.encrypt(password));
        if (!propFile.getParentFile().exists()) {
          propFile.getParentFile().mkdirs();
        }
        FileOutputStream propsStream = new FileOutputStream(propFile);
        props.store(propsStream, "Mediawiki JAPI credentials for " + wikiid);
        propsStream.close();
      }
    } catch (IOException e1) {
      LOGGER.log(Level.SEVERE, e1.getMessage());
    } catch (GeneralSecurityException e1) {
      LOGGER.log(Level.SEVERE, e1.getMessage());
    } catch (CmdLineException e) {
      parser.printUsage(System.err);
    }
  }

  /**
   * help text
   * 
   * @param wikiId
   * @param siteurl
   * @return - the help text
   */
  public static String help(String wikiId, String siteurl) {
    File propFile = getPropertyFile(wikiId);
    String help = "Need to be able to read Credentials for \n\t" + siteurl
        + "\nfrom " + propFile.getPath() + "\n";
    help += "Please run \n";
    help += "\tjava -cp target/classes com.bitplan.mediawiki.japi.user.WikiUser "
        + wikiId + "\n";
    help += "to create it. Then restart your tests.";
    return help;
  }

  /**
   * main program
   * 
   * @param args
   */
  public static void main(String args[]) {
    WikiUser wikiUser = new WikiUser();
    wikiUser.createIniFile(args);
  }

}
