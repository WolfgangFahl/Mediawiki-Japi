JMediawiki
==========

Client for Mediawiki Api

### Purpose
This is a Java API for the Mediawiki web API described at 
http://www.mediawiki.org/wiki/API:Main_page

### Status
Early Alpha state - not release on Maven Central yet.

### Project
* Open Source hosted at https://github.com/WolfgangFahl/JMediawiki
* Issues via https://github.com/WolfgangFahl/JMediawiki/issues
* Apache License
* Maven based Java project including JUnit 4 tests.
* Project page at http://wolfgangfahl.github.io/JMediawiki/

Available at http://search.maven.org/#artifactdetails|com.bitplan|JMediawiki|0.0.1|jar

Maven dependency:

```
<dependency>
  <groupId>com.bitplan</groupId>
  <artifactId>JMediawiki</artifactId>
  <version>0.0.1</version>
</dependency>
```

### How to build
* git clone https://github.com/WolfgangFahl/JMediawiki
* cd JMediawiki
* mvn install

### How to use

## Examples:

### Sample query:get a single page
http://www.mediawiki.org/wiki/API:Query#Sample_query


```
  /**
	 * http://www.mediawiki.org/wiki/API:Query#Sample_query
	 * http://en.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=content&titles=Main%20Page&format=xml
	 * @throws Exception 
	 */
	@Test
	public void testSampleQuery() throws Exception {
		JMediawiki wiki=new JMediawiki("http://en.wikipedia.org");
		Api api = wiki.getQueryResult("&prop=revisions&rvprop=content&titles=Main%20Page");
		Page page=api.getQuery().getPages().get(0);
		assertEquals("Main Page",page.getTitle());
		String content=page.getRevisions().get(0).getValue();	
		assertTrue(content.contains("Wikipedia"));
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
16 Java classes have been generated that allow some 9 JUnit tests to pass.
  
## Version history
* 0.0.1 - 2015-01-01: first version
* 0.0.2 - 2015-01-01: not published via maven central yet
