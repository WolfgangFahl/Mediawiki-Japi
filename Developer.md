JMediawiki Developer info
=========================

## Testenvironment
To test the JMediawiki API library some Example Wikis are used.

### Read-only tests 
* http://www.mediawiki.org
A login attempt is part of the tests - you'll need credentials to run these tests successfully.

### Write tests
* http://localhost/mediawiki-test2
You need a local test mediawiki to run these tests successfully. 

### Credentials for the test example Mediawikis
The credentials for the test example Mediawikis are fetched from ini files in the .jmediawiki subdirectory
of your home directory. E.g. if your username is bob and the id of the Mediawiki is "mediawiki_org" then you'll find
the ini file at $HOME/bob_mediawiki_org.ini.

#### Example for a credential ini file

```ini
#JMediawiki settings
#Fri Jan 02 19:58:49 CET 2015
secret=5hKnFOyU1uE\=
user=Scott
email=scott@tiger.org
salt=w75pcoUN
cypher=YYmhAs86ygNnLbs@b9dNq36afytEzkCm
```