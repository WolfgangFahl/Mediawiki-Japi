### Status
- Implemented Features are limited to what the test cases of the current version check
- tests run against current wikipedia installations as well as 1.23.17,1.25.6,1.27.3 and 1.29.1 test wikis 

## Examples
See also [Examples](http://mediawiki-japi.bitplan.com/mediawiki-japi/index.php/Examples) on the Mediawiki-Japi Wiki

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

