### Mediawiki-Japi
[Java library to call Mediawiki API described at http://www.mediawiki.org/wiki/API:Main_page](http://mediawiki-japi.bitplan.com/index.php/Main_Page)

[![Travis (.org)](https://img.shields.io/travis/WolfgangFahl/Mediawiki-Japi.svg)](https://travis-ci.org/WolfgangFahl/Mediawiki-Japi)
[![Maven Central](https://img.shields.io/maven-central/v/com.bitplan/mediawiki-japi.svg)](https://search.maven.org/artifact/com.bitplan/mediawiki-japi/0.1.05/jar)

[![GitHub issues](https://img.shields.io/github/issues/WolfgangFahl/Mediawiki-Japi.svg)](https://github.com/WolfgangFahl/Mediawiki-Japi/issues)
[![GitHub issues](https://img.shields.io/github/issues-closed/WolfgangFahl/Mediawiki-Japi.svg)](https://github.com/WolfgangFahl/Mediawiki-Japi/issues/?q=is%3Aissue+is%3Aclosed)
[![GitHub](https://img.shields.io/github/license/WolfgangFahl/Mediawiki-Japi.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![BITPlan](http://wiki.bitplan.com/images/wiki/thumb/3/38/BITPlanLogoFontLessTransparent.png/198px-BITPlanLogoFontLessTransparent.png)](http://www.bitplan.com)

### Documentation
* [Wiki](http://mediawiki-japi.bitplan.com/index.php/Main_Page)
* [Mediawiki-Japi Project pages](https://WolfgangFahl.github.io/Mediawiki-Japi)
* [Javadoc](https://WolfgangFahl.github.io/Mediawiki-Japi/apidocs/index.html)
* [Test-Report](https://WolfgangFahl.github.io/Mediawiki-Japi/surefire-report.html)
### Maven dependency

Maven dependency
```xml
<!-- Java library to call Mediawiki API described at http://www.mediawiki.org/wiki/API:Main_page http://mediawiki-japi.bitplan.com/index.php/Main_Page -->
<dependency>
  <groupId>com.bitplan</groupId>
  <artifactId>mediawiki-japi</artifactId>
  <version>0.1.05</version>
</dependency>
```

[Current release at repo1.maven.org](http://repo1.maven.org/maven2/com/bitplan/mediawiki-japi/0.1.05/)

### How to build
```
git clone https://github.com/WolfgangFahl/Mediawiki-Japi
cd Mediawiki-Japi
mvn install
```
### Status
- Implemented Features are limited to what the test cases of the current version check
- tests run against current wikipedia installations as well as 1.23.17,1.25.6,1.27.3 and 1.29.1 test wikis 

## Examples
See also [Examples](http://mediawiki-japi.bitplan.com/index.php/Examples) on the Mediawiki-Japi Wiki

### Sample query:get a single page
http://www.mediawiki.org/wiki/API:Query#Sample_query

```java
	Mediawiki wiki=new Mediawiki("http://en.wikipedia.org");
	String content=wiki.getPageContent("Main Page");
```

#### junit test
```java
  /**
   * http://www.mediawiki.org/wiki/API:Query#Sample_query
   * http://en.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=content&titles=Main%20Page&format=xml
   * @throws Exception 
   */
	@Test
	public void testSampleQuery() throws Exception {
		Mediawiki wiki=new Mediawiki("http://en.wikipedia.org");
		String content=wiki.getPageContent("Main Page");
		assertTrue(content.contains("Wikipedia"));
	}
```

### login/logout
http://www.mediawiki.org/wiki/API:Login

```java
Login login=wiki.login("scott","tiger");
wiki.logout();
```

#### junit test
```java
  /**
	 * test Login and logout 
	 * see <a href='http://www.mediawiki.org/wiki/API:Login'>API:Login</a>
	 * @throws Exception
	 */
	@Test
	public void testLogin() throws Exception {
		WikiUser wuser=WikiUser.getUser("mediawiki_org");
		Login login=wiki.login(wuser.getUsername(),wuser.getPassword());
		assertEquals("Success",login.getResult());
		assertNotNull(login.getLguserid());
		assertEquals(wuser.getUsername(),login.getLgusername());
		assertNotNull(login.getLgtoken());
		wiki.logout();
	}
```
## Design decisions
[Jaxb-Generator](http://mediawiki-japi.bitplan.com/mediawiki-japi/index.php/Jaxbgenerator)

This is a semi-automatic process. The resulting java code needs to be adapted to be workable. For a start
18 Java classes have been generated that allow some 19 JUnit tests to pass (to a total of 43 including the 24
unit tests supplied with Wiki.java).

## Version history
* 0.0.1  - 2015-01-01: first version as JMediawiki
* 0.0.2  - 2015-01-18: fixes issues #1 - #7
* 0.0.3  - 2015-01-20: fixes issue #8 refactors to multi implementation approach with guice
* 0.0.4  - 2015-04-05: adds section handling
* 0.0.5  - 2015-06-14: improves section handling
                       handles uploads in copyToWiki
					   fixes issue with test on en.wikipedia.org
* 0.0.6  - 2015-07-02: fixes revprop handling											
* 0.0.7  - 2015-07-08: fixes #14 namespace handling in copy
                       adds #15 - delete function
* 0.0.8  - 2015-08-26: fixes #16 allImages support (timestamp based for the time being)  
* 0.0.9  - 2015-10-10: fixes #17 to #18 and #19: ignore superfluous newlines in xml api results (if any)
* 0.0.10 - 2015-10-12: fixes #20
* 0.0.11 - 2015-11-09: fixes #21
* 0.0.12 - 2016-01-09: fixes issues #21 to #24
* 0.0.13 - 2016-01-19: fixes #25
* 0.0.14 - 2016-03-12: fixes #26,#27
* 0.0.15 - 2016-10-22: fixes #30,#31
* 0.0.16 - 2016-11-16: fixes #32
* 0.0.17 - 2016-11-16: fixes #33
* 0.0.18 - 2016-11-17: fixes #34 upgrades to Jersey 1.19.3
* 0.0.19 - 2017-05-30: pr #37 login to domain support
           2017-08-24: adds travis build check
* 0.0.20 - 2017-09-13: fixes #38
* 0.0.21 - 2017-09-16: fixes #39
* 0.1.01 - 2018-01-25: Java 8 and first steps for json support
* 0.1.02 - 2018-08-20: fixes #40,#41  
* 0.1.03 - 2019-03-20: merges #43
* 0.1.04 - 2019      : not released to maven central
* 0.1.05 - 2020-03-16: fixes #49 - adds command line mode and upgrades some dependencies
