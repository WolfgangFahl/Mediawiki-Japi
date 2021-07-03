/**
 *
 * This file is part of the https://github.com/WolfgangFahl/Mediawiki-Japi open source project
 *
 * Copyright 2015-2021 BITPlan GmbH https://github.com/BITPlan
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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.bitplan.mediawiki.japi.api.Api;
import com.bitplan.mediawiki.japi.jaxb.JaxbFactory;

/**
 * Test XML handling
 * 
 * @author wf
 *
 */
public class TestXML extends APITestbase {

	/**
	 * test unmarshaling an xml message with extra warning
	 * @throws Exception 
	 */
	@Test
	public void testXMLDecode() throws Exception {
		String xml="<?xml version=\"1.0\"?><api><edit result=\"Success\" pageid=\"2\" title=\"Testpage 1\" contentmodel=\"wikitext\" nochange=\"\" watched=\"\" /></api><br />\n"
				+ "<b>Deprecated</b>:  Use of InternalParseBeforeSanitize hook (used in VariablesHooks::onInternalParseBeforeSanitize) was deprecated in MediaWiki 1.35. [Called from MediaWiki\\HookContainer\\HookContainer::run in /var/www/html/includes/HookContainer/HookContainer.php at line 137] in <b>/var/www/html/includes/debug/MWDebug.php</b> on line <b>376</b><br />";
			
		JaxbFactory<Api> apijaxbfactory = new JaxbFactory<Api>(Api.class);
		Api api = apijaxbfactory.fromXML(xml);
		assertEquals("Testpage 1",api.getEdit().getTitle());
	}
}
