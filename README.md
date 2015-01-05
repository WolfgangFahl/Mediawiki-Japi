Mediawiki-Japi
==============

This is a Java API for the Mediawiki web API described at 

http://www.mediawiki.org/wiki/API:Main_page

## Project 

### Documentation
* [Javadoc](http://wolfgangfahl.github.io/Mediawiki-Japi/apidocs/index.html)
* [Examples](http://mediawiki-japi.bitplan.com/mediawiki-japi/index.php/Examples)
* [Wiki](http://mediawiki-japi.bitplan.com)
* [Developer info](http://mediawiki-japi.bitplan.com/mediawiki-japi/index.php/Developer_Info)

### Status
- Implemented Features are limited to what the test cases of the current version check

### Project info
* Open Source hosted at https://github.com/WolfgangFahl/Mediawiki-Japi
* Issues via https://github.com/WolfgangFahl/Mediawiki-Japi/issues
* Apache License
* Maven based Java project including JUnit 4 tests.
* Project page at http://wolfgangfahl.github.io/Mediawiki-Japi/

### Distribution
Available at Maven Central see 

http://search.maven.org/#artifactdetails|com.bitplan|mediawiki-japi|0.0.2|jar

Maven dependency:

```xml
<dependency>
  <groupId>com.bitplan</groupId>
  <artifactId>mediawiki-japi</artifactId>
  <version>0.0.2</version>
</dependency>
```

### How to build
```
git clone https://github.com/WolfgangFahl/Mediawiki-Japi
cd Mediawiki-Japi
mvn install -DskipTests=true -Dgpg.skip=true
```

## Examples
See also [Examples] (http://mediawiki-japi.bitplan.com/mediawiki-japi/index.php/Examples) on the Mediawiki-Japi Wiki

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
18 Java classes have been generated that allow some 17 JUnit tests to pass.
  
## Version history
* 0.0.1 - 2015-01-01: first version as JMediawiki
* 0.0.2 - 2015-01-04: fixes issues #1 and #2 and #4 (not released yet)
