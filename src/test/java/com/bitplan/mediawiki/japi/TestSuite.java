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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Testsuite for Mediawiki-Japi
 * @author wf
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ TestApiJaxb.class, TestAPI_Query.class,
		TestAPI_Meta.class, TestAPI_Recentchanges.class, TestAPI_Login.class, TestAPI_Edit.class,
		TestUsage.class,TestCommandLine.class,TestGetCSV.class,org.wikipedia.WikiUnitTest.class})
public class TestSuite {

}
