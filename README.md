JMediawiki
==========

This is a Java API for the Mediawiki web API described at 

http://www.mediawiki.org/wiki/API:Main_page

## Project 

### Documentation
* Javadoc: http://wolfgangfahl.github.io/JMediawiki/apidocs/index.html
* [Examples](#examples)

### Status
- Implemented Features are limited to what the test cases of the current version check

### Project info
* Open Source hosted at https://github.com/WolfgangFahl/JMediawiki
* Issues via https://github.com/WolfgangFahl/JMediawiki/issues
* Apache License
* Maven based Java project including JUnit 4 tests.
* Project page at http://wolfgangfahl.github.io/JMediawiki/


### Distribution
Available at Maven Central see 

http://search.maven.org/#artifactdetails|com.bitplan|jmediawiki|0.0.1|jar

Maven dependency:

```
<dependency>
  <groupId>com.bitplan</groupId>
  <artifactId>jmediawiki</artifactId>
  <version>0.0.1</version>
</dependency>
```

### How to build
* git clone https://github.com/WolfgangFahl/JMediawiki
* cd JMediawiki
* mvn install


## Examples

### Sample query:get a single page
http://www.mediawiki.org/wiki/API:Query#Sample_query

```java
	JMediawiki wiki=new JMediawiki("http://en.wikipedia.org");
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
		JMediawiki wiki=new JMediawiki("http://en.wikipedia.org");
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
The Mediawiki API supports XML and Json encoding of API calls. The XML version of things will be deprecated soon. The
original idea to create a schema was dropped in 2014 see:
* https://phabricator.wikimedia.org/T16025
  
Still it would be nice to generate the code for the API access. JAXB is choosen to attempt this. 
There is a bash script createschema which
* runs a sample query
* stores the resulting xml
* generates xsd from the xml
* generates java code from the xml

This is a semi-automatic process. The resulting java code needs to be adapted to be workable. For a start
15 Java classes have been generated that allow some 12 JUnit tests to pass.
  
## Version history
* 0.0.1 - 2015-01-01: first version
* 0.0.2 - 2015-01-02: not published via maven central yet
