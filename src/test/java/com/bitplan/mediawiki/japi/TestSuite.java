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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Testsuite for Mediawiki-Japi
 * 
 * @author wf
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ TestIssue23.class,TestIssue47.class, TestApiJaxb.class, TestAPI_Query.class,
    TestAPI_Meta.class, TestAPI_Recentchanges.class, TestAPI_Login.class,
    TestAPI_Edit.class, TestAPI_Parse.class, TestAPI_Allpages.class,
    TestAPI_SMW.class,
    TestAPI_Upload.class,
    TestProtectionMarker.class, TestUsage.class, TestCommandLine.class,
    TestGetCSV.class,TestPageTitle.class, TestCreateAccount.class, org.wikipedia.WikiUnitTest.class })
public class TestSuite {

}
