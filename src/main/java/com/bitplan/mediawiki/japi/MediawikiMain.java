package com.bitplan.mediawiki.japi;

import java.io.File;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

import com.bitplan.mediawiki.japi.user.WikiUser;

/**
 * command line interface to Mediawiki API
 * 
 * @author wf
 *
 */
public class MediawikiMain extends Mediawiki {
  static int exitCode;
  private CmdLineParser parser;

  @Option(name = "-h", aliases = { "--help" }, usage = "help\nshow this usage")
  boolean showHelp = false;

  @Option(name = "-i", aliases = {
      "--imageLimit" }, usage = "the maximum number of images per page to push")
  int imageLimit = 100;

  @Option(name = "-l", aliases = {
      "--login" }, usage = "login to source wiki for access permission")
  boolean login = false;

  @Option(name = "-s", aliases = { "--source" }, usage = "the source wiki")
  String sourceWiki = null;

  @Option(name = "-t", aliases = { "--target" }, usage = "the target wiki")
  String targetWiki = null;

  @Option(name = "--test", usage = "test")
  boolean test = false;

  @Option(name = "-a", aliases = { "--all" }, usage = "all available wikis")
  boolean all = false;

  @Option(name = "-u", aliases = {
      "--user" }, usage = "activates credential mode\nuse -u -h to see options\nsee http://mediawiki-japi.bitplan.com/index.php/CommandLine")
  String username = null;

  @Option(name = "-c", aliases = {
      "--check" }, usage = "check mode - tries access without writing to target")
  boolean check = false;

  @Option(name = "--cookie", usage = "use given cookie for download")
  String cookie = null;

  @Option(name = "-p", aliases = {
      "--pages" }, handler = StringArrayOptionHandler.class, usage = "the pages to be transferred")
  String[] pageTitles = {};

  @Option(name = "-v", aliases = {
      "--version" }, usage = "showVersion\nshow current version if this switch is used")
  boolean showVersion = false;

  /**
   * default constructor
   * 
   * @throws Exception
   */
  public MediawikiMain() throws Exception {
    super();
  }

  /**
   * show the Version
   */
  public static void showVersion() {
    System.err.println("Mediawiki-Japi Version: " + VERSION);
    System.err.println();
    System.err
        .println(" github: https://github.com/WolfgangFahl/Mediawiki-Japi");
    System.err.println("");
  }

  /**
   * show a usage
   */
  public void usage(String msg) {
    System.err.println(msg);

    showVersion();
    String usageMsg = String.format("  usage: java %s",
        this.getClass().getName());
    System.err.println(usageMsg);
    parser.printUsage(System.err);
    exitCode = 1;
  }

  /**
   * show Help
   */
  public void showHelp() {
    String msg = "Help\n" + "Mediawiki-Japi version " + VERSION
        + "\nPlease visit http://mediawiki-japi.bitplan.com for usage instructions";
    usage(msg);
  }

  /**
   * show the result of the given check
   * @param ok - if true use System.out and append ✅ else System.err and append ❌
   * @param format - String.format format
   * @param args - String.format args
   */
  public void showResult(boolean ok,String format,Object ...args) {
    String msg=String.format(format, args);
    String check=ok? "✅" : "❌";
    if (ok)
      System.out.println(msg+check);
    else
      System.err.println(msg+check);
  }
  /**
   * test the given property file
   */
  public boolean testPropertyFile(File propertyFile) {
    WikiUser wikiUser = new WikiUser();
    if (wikiUser.testPropertyFile(propertyFile)) {
      SSLWiki wiki;
      try {
        wiki = new SSLWiki(wikiUser);
        wiki.login();
        boolean versionOk = wiki.getVersion().equals(wikiUser.getVersion());
        showResult(versionOk,"\tversion: %s", wiki.getVersion());
        SiteInfo siteInfo=wiki.getSiteInfo();
        System.out.println(String.format("\tsitename: %s",siteInfo.getGeneral().getSitename()));
        System.out.println(String.format("\t    lang: %s",siteInfo.getGeneral().getLang()));  
      } catch (Exception e) {
        System.err.println(String.format("\t❌:%s", e.getMessage()));
        String cmd = String.format(
            "./run -w %s -u %s -e %s -l %s -s '%s' -v '%s' -y --password '**'",
            wikiUser.getWikiId(), wikiUser.getUsername(), wikiUser.getEmail(),
            wikiUser.getUrl(), wikiUser.getScriptPath(), wikiUser.getVersion());
        System.err.println(String
            .format("To update your password your might want to run\n%s", cmd));
        return false;
      }
      return true;
    } else
      return false;
  }

  /**
   * main instance - this is the non-static version of main - it will run as a
   * static main would but return it's exitCode to the static main the static
   * main will then decide whether to do a System.exit(exitCode) or not.
   * 
   * @param args
   *          - command line arguments
   * @return - the exit Code to be used by the static main program
   */
  protected int maininstance(String[] args) {
    parser = new CmdLineParser(this);
    try {
      boolean credentialMode = false;
      for (String arg : args) {
        if ("-u".equals(arg))
          credentialMode = true;
      }
      // credential mode - call WikiUser
      if (credentialMode) {
        WikiUser wikiUser = new WikiUser();
        return wikiUser.maininstance(args);
      }
      this.username = WikiUser.getSystemUserName();
      parser.parseArgument(args);
      if (debug)
        showVersion();
      if (this.showVersion) {
        showVersion();
      } else if (this.showHelp) {
        showHelp();
      } else if (this.test) {
        if (this.all) {
          for (File propertyFile : WikiUser.getPropertyFiles()) {
            if (!this.testPropertyFile(propertyFile)) {
              exitCode++;
            }
          }
        } else {
          File propFile = WikiUser.getPropertyFile(this.sourceWiki, username);
          if (!this.testPropertyFile(propFile)) {
            exitCode++;
          }
        }
        String msg = String.format("There are %3d problems", exitCode);
        System.err.println(msg);
      } else if (this.targetWiki != null && this.sourceWiki != null
          && this.pageTitles != null) {
        PushPages.debug = debug;
        PushPages pp = new PushPages(this.sourceWiki, this.targetWiki,
            imageLimit, login);
        pp.setCheck(check);
        pp.setCookie(cookie);
        pp.setShowDebug(debug);
        pp.push(pageTitles);
      } else {
        showHelp();
      }
    } catch (CmdLineException e) {
      // handling of wrong arguments
      usage(e.getMessage());
    } catch (Exception e) {
      handle(e);
      exitCode = 1;
    }
    return exitCode;
  }

  /**
   * entry point e.g. for java -jar called provides a command line interface
   * 
   * @param args
   */
  public static void main(String args[]) {
    MediawikiMain wiki;
    Mediawiki.initLog4J();
    try {
      wiki = new MediawikiMain();
      int result = wiki.maininstance(args);
      if (!testMode && result != 0)
        System.exit(result);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
