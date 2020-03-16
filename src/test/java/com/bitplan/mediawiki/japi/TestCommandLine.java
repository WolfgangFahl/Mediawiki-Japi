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

	boolean debug = true;

	/**
	 * test the Mediawiki-Japi command line
	 * 
	 * @param args
	 *          - command line arguments
	 * @param expectedExit
	 *          - the expected exit code
	 */
	public void callMediawiki(String args[], int expectedExit) {
		MediawikiMain.testMode = true;
		MediawikiMain.main(args);
		assertEquals(expectedExit, MediawikiMain.exitCode);
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
		String[] args = { "-h","-d" };
		GrabSyserr.start();
		callMediawiki(args, 1);
		String output = GrabSyserr.end();
		if (debug)
			Mediawiki.LOGGER.log(Level.INFO, output);
		assertTrue(output.contains("show this usage"));
	}

}
