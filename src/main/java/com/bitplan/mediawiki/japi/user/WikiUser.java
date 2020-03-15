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
  public static boolean testMode = false;
  @Option(name = "-h", aliases = { "--help" }, usage = "help\nshow this usage")
  boolean showHelp = false;
  @Option(name = "-s", aliases = { "--scriptPath" }, usage = "scriptPath")
  String scriptPath = "/";
  @Option(name = "-l", aliases = { "--url" }, usage = "url")
  private String url;
  @Option(name = "-u", aliases = { "--user" }, usage = "username")
  String username;
  @Option(name = "-p", aliases = { "--password" }, usage = "password")
  String password;
  @Option(name = "-e", aliases = { "--email" }, usage = "email")
  String email;
  @Option(name = "-w", aliases = { "--wikiId" }, usage = "wiki id")
  String wikiid;
  @Option(name = "-t", aliases = { "--test" }, usage = "test")
  boolean test = false;
  @Option(name = "-a", aliases = { "--all" }, usage = "all available wikis")
  boolean all = false;

  @Option(name = "-y", aliases = { "--store" }, usage = "store without asking")
  boolean yes = false;

  private CmdLineParser parser;

  /**
   * construct me from the given parameters
   * @param wikiId
   * @param url
   * @param scriptPath
   */
  public WikiUser(String wikiId, String url, String scriptPath) {
    this.wikiid=wikiId;
    this.url=url;
    this.scriptPath=scriptPath;
  }

  public WikiUser() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @param url
   *          the url to set
   */
  public void setUrl(String url) {
    this.url = url;
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
   * @return the scriptPath
   */
  public String getScriptPath() {
    return scriptPath;
  }

  /**
   * @param scriptPath
   *          the scriptPath to set
   */
  public void setScriptPath(String scriptPath) {
    this.scriptPath = scriptPath;
  }

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

  public String getWikiid() {
    return wikiid;
  }

  public void setWikiid(String wikiid) {
    this.wikiid = wikiid;
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
  public String getInput(String name, BufferedReader br) throws IOException {
    // prompt the user to enter the given name
    System.out.print("Please Enter " + name + ": ");

    String value = br.readLine();
    return value;
  }

  /**
   * get the directory for the properties
   * 
   * @return - the properties directory
   */
  public static File getPropertyDir() {
    String userPropertiesFileDir = System.getProperty("user.home")
        + "/.mediawiki-japi";
    File propertydir = new File(userPropertiesFileDir);
    if (!propertydir.exists())
      propertydir.mkdirs();
    return propertydir;
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
    String userPropertiesFileName = user + "_" + wikiId + ".ini";
    File propFile = new File(getPropertyDir(), userPropertiesFileName);
    return propFile;
  }

  /**
   * get the Properties for the given wikiId
   * 
   * @param wikiId
   * @return the properties
   * @throws IOException
   * @throws FileNotFoundException
   */
  public static Properties getProperties(String wikiId, String user)
      throws FileNotFoundException, IOException {
    File propFile = getPropertyFile(wikiId, user);
    return getProperties(propFile);
  }

  /**
   * get the properties for the given propertyFile
   * 
   * @param propFile
   * @return
   * @throws IOException
   * @throws FileNotFoundException
   */
  public static Properties getProperties(File propFile)
      throws FileNotFoundException, IOException {
    Properties props = new Properties();
    props.load(new FileReader(propFile));
    return props;
  }

  /**
   * get the Wiki User
   * @param wikiId
   * @return the wiki user
   */
  public static WikiUser getUser(String wikiId) {
    String user = System.getProperty("user.name");
    return getUser(wikiId, user);
  }

  /**
   * get the Wiki user for the given wikiid
   * 
   * @param wikiId
   *          - the id of the wiki
   * @return a Wikiuser for this site
   */
  public static WikiUser getUser(String wikiId, String user) {
    WikiUser result = null;
    try {
      Properties props = getProperties(wikiId, user);
      result = new WikiUser();
      result.wikiid=wikiId;
      result.initFromProperties(props);
      Crypt pcf = new Crypt(props.getProperty("cypher"),
          props.getProperty("salt"));
      result.setPassword(pcf.decrypt(props.getProperty("secret")));
    } catch (FileNotFoundException e) {
      String msg = help(wikiId, user);
      LOGGER.log(Level.SEVERE, msg);
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
    } catch (GeneralSecurityException e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
    }
    return result;
  }

  /**
   * initialize me from the given properties
   * 
   * @param props
   */
  public void initFromProperties(Properties props) {
    setUsername(props.getProperty("user"));
    setEmail(props.getProperty("email"));
    setUrl(props.getProperty("url"));
    setScriptPath(props.getProperty("scriptPath"));
  }

  /**
   * create a credentials ini file
   */
  public void createIniFile() {
    try {
      // open up standard input
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      if (wikiid == null)
        wikiid = getInput("wiki id", br);
      if (url==null)
        url=getInput("url",br);
      if (scriptPath==null)
        scriptPath=getInput("scriptPath",br);
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
        props.setProperty("wikiId",wikiid);
        props.setProperty("cypher", lCrypt.getCypher());
        props.setProperty("salt", lCrypt.getSalt());
        props.setProperty("url",url);
        props.setProperty("scriptPath", scriptPath);
        props.setProperty("user", username);
        props.setProperty("email", email);
        props.setProperty("secret", lCrypt.encrypt(password));
        FileOutputStream propsStream = new FileOutputStream(propFile);
        props.store(propsStream, "Mediawiki JAPI credentials for " + wikiid);
        propsStream.close();
        System.out.println("created/updated properties in "+propFile.getPath());
        
      }
    } catch (IOException e1) {
      LOGGER.log(Level.SEVERE, e1.getMessage());
    } catch (GeneralSecurityException e1) {
      LOGGER.log(Level.SEVERE, e1.getMessage());
    }
  }

  /**
   * help text
   * 
   * @param wikiId
   * @param user
   * @return - the help text
   */
  public static String help(String wikiId, String user) {
    File propFile = getPropertyFile(wikiId, user);
    String help = "Need to be able to read Credentials for wiki withid \n\t"
        + wikiId + "\nfrom " + propFile.getPath() + "\n";
    help += "Please run \n";
    help += "\tjava -cp target/classes com.bitplan.mediawiki.japi.user.WikiUser "
        + wikiId + "\n";
    help += "to create it. Then restart your tests.\n";
    help += "see http://mediawiki-japi.bitplan.com/index.php/CommandLine\n";
    help += "for more options and details";
    return help;
  }

  /**
   * test the given propertyFile
   * 
   * @param propFile
   * @throws Exception
   */
  public boolean testPropertyFile(File propFile) {
    boolean problem=true;
    try {
      Properties props = getProperties(propFile);
      this.initFromProperties(props);
      problem = url == null || scriptPath == null;
      String info = String.format("%s:\n\turl=%s\n\tscriptPath=%s\n\tuser=%s\n\te-mail=%s",
          propFile.getName(),url == null ? "?" : url,scriptPath==null?"?":scriptPath, username, email);
      if (problem)
        System.err.println(info);
      else
        System.out.println(info);
    } catch (Exception e) {
      String msg = String.format("error %s with property file %s",
          e.getMessage(), propFile.getPath());
      System.err.println(msg);
    }
    return !problem;
  }
  
  private void showHelp() {
    String usageMsg = String.format("  usage: java %s\nPlease visit http://mediawiki-japi.bitplan.com for usage instructions\n",
        this.getClass().getName());
    
    System.err.println(usageMsg);
    parser.printUsage(System.err);
    
  }

  /**
   * main function as instance function
   * 
   * @param args
   * @return - the return code
   */
  public int maininstance(String[] args) {
    int returnCode=0;
    try {
      parser = new CmdLineParser(this);
      parser.parseArgument(args);
      if (this.showHelp) {
        this.showHelp();
      } else if (this.test) {
        if (this.all) {
          File propertyDir = getPropertyDir();
          for (String propertyFileName : propertyDir.list()) {
            if (!this.testPropertyFile(new File(propertyDir, propertyFileName))) {
              returnCode++;
            }
          }
        } else {
          File propFile = getPropertyFile(wikiid, username);
          if (!this.testPropertyFile(propFile)) {
            returnCode++;
          }
        }
        String msg=String.format("There are %3d problems",returnCode);
        System.err.println(msg);
      } else {
        createIniFile();
      }
    } catch (CmdLineException e) {
      parser.printUsage(System.err);
      return 1;
    }
    return returnCode;
  }

  /**
   * main program
   * 
   * @param args
   * @return
   */
  public static int main(String args[]) {
    WikiUser wikiUser = new WikiUser();
    return wikiUser.maininstance(args);
  }

}
