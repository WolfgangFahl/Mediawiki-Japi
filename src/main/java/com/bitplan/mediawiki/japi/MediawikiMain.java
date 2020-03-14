package com.bitplan.mediawiki.japi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

/**
 * command line interface to Mediawiki API
 * 
 * @author wf
 *
 */
public class MediawikiMain extends Mediawiki {

  /**
   * default contructor
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
        + "Please visit http://mediawiki-japi.bitplan.com for usage instructions";
    usage(msg);
  }

  private CmdLineParser parser;

  @Option(name = "-h", aliases = { "--help" }, usage = "help\nshow this usage")
  boolean showHelp = false;

  @Option(name = "-l", aliases = {
      "--imageLimit" }, usage = "the maximum number of images per page to push")
  int imageLimit = 100;

  @Option(name = "-s", aliases = { "--source" }, usage = "the source wiki")
  String sourceWiki = null;

  @Option(name = "-t", aliases = { "--target" }, usage = "the target wiki")
  String targetWiki = null;

  @Option(name = "-p", aliases = {
      "--pages" }, handler = StringArrayOptionHandler.class, usage = "the pages to be transferred")
  String[] pageTitles = {};

  @Option(name = "-v", aliases = {
      "--version" }, usage = "showVersion\nshow current version if this switch is used")
  boolean showVersion = false;

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
      parser.parseArgument(args);
      if (debug)
        showVersion();
      if (this.showVersion) {
        showVersion();
      } else if (this.showHelp) {
        showHelp();
      } else if (this.targetWiki != null && this.sourceWiki != null
          && this.pageTitles != null) {
        PushPages pp = new PushPages(this.sourceWiki, this.targetWiki,
            imageLimit);
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
