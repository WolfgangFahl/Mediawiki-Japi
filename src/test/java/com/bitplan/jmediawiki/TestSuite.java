/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 */
package com.bitplan.jmediawiki;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({ TestApiJaxb.class,TestAPI_Query.class, TestAPI_Meta.class, TestAPI_Recentchanges.class,TestAPI_Login.class,TestUsage.class})
public class TestSuite {

}
