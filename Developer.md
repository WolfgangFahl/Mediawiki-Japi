JMediawiki Developer info
=========================

## Testenvironment
There is a JUnit 4 Testsuite for JMediawiki API library that accesses some Example Wikis for the tests.
Please find below the list of example wikis being used 

### Read-only tests 
* http://www.mediawiki.org

A login attempt is part of the tests - you'll need credentials at http://www.mediawiki.org 
to run these tests successfully. You might want to create an account
at https://www.mediawiki.org/w/index.php?title=Special:UserLogin&type=signup

### Read/Write tests
* http://jmediawiki.bitplan.com
You need full api write access to the wikis on http://jmediawiki.bitplan.com
to run these tests.

### Credentials for the test example Mediawikis
The credentials for the test example Mediawikis are fetched from ini files in the .jmediawiki subdirectory
of your home directory. E.g. if your username is bob and the id of the Mediawiki is "mediawiki_org" then you'll find
the ini file at $HOME/bob_mediawiki_org.ini.

If the credentials ini files do not exist yet they are created interactively during the test run.

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