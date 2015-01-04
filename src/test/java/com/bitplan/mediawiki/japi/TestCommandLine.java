/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 */
package com.bitplan.mediawiki.japi;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;

import org.junit.Test;

/**
 * test the CommandLine Handling
 * 
 * @author wf
 *
 */
public class TestCommandLine {

	boolean debug = false;

	/**
	 * test the Mediawiki-Japi command line
	 * 
	 * @param args
	 *          - command line arguments
	 * @param expectedExit
	 *          - the expected exit code
	 */
	public void callMediawiki(String args[], int expectedExit) {
		Mediawiki.testMode = true;
		Mediawiki.main(args);
		assertEquals(expectedExit, Mediawiki.exitCode);
	}

	static class GrabSyserr {
		static PrintStream oldSyserr;
		static ByteArrayOutputStream baos;

		public static PrintStream start() {
			// Create a stream to hold the output
			baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			// IMPORTANT: Save the old System.err!
			oldSyserr = System.err;
			// Tell Java to use your special stream
			System.setErr(ps);
			return ps;
		}

		public static String end() {
			// Put things back
			System.err.flush();
			System.setErr(oldSyserr);
			// Show what happened
			return baos.toString();
		}
	}

	@Test
	public void testUsage() {
		String[] args = { "-h" };
		GrabSyserr.start();
		callMediawiki(args, 1);
		String output = GrabSyserr.end();
		if (debug)
			Mediawiki.LOGGER.log(Level.INFO, output);
		assertTrue(output.contains("show this usage"));
	}

}
