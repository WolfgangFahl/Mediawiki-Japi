package org.wikipedia;

import java.net.URL;
import java.util.HashMap;

import com.bitplan.mediawiki.japi.MediawikiApi;
import com.bitplan.mediawiki.japi.api.Edit;
import com.bitplan.mediawiki.japi.api.General;
import com.bitplan.mediawiki.japi.api.Login;

/**
 * Mediawiki-Japi compatible Wrapper for Wiki.java
 * 
 * @author wf
 *
 */
public class Mediawiki implements MediawikiApi {
	// delegate
	Wiki wiki;

	/**
	 * set to true if exceptions should be thrown on Error
	 */
	protected boolean throwExceptionOnError = true;

	/**
	 * @return the throwExceptionOnError
	 */
	public boolean isThrowExceptionOnError() {
		return throwExceptionOnError;
	}

	/**
	 * @param throwExceptionOnError
	 *          the throwExceptionOnError to set
	 */
	public void setThrowExceptionOnError(boolean throwExceptionOnError) {
		this.throwExceptionOnError = throwExceptionOnError;
	}

	@Override
	public String getSiteurl() {
		// FIXME
		return null;
	}

	@Override
	public void setSiteurl(String siteurl) throws Exception {
		URL url = new URL(siteurl);
		String domain = url.getHost();
		domain = domain.startsWith("www.") ? domain.substring(4) : domain;
		String scriptPath=url.getPath();
		if ("".equals(scriptPath)) {
			scriptPath="/w";
		}
		Wiki.PROT=url.getProtocol();
		
		wiki = new Wiki(domain,scriptPath);
	}

	@Override
	public String getVersion() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public General getSiteInfo() throws Exception {
		HashMap<String, Object> siteinfo = wiki.getSiteInfo();
		General general = new General();
		general.setGenerator((String) siteinfo.get("generator"));
		return general;
	}

	@Override
	public Login login(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPageContent(String pageTitle) throws Exception {
		String result = wiki.getPageText(pageTitle);
		return result;
	}

	@Override
	public void logout() throws Exception {
		wiki.logout();
	}

	@Override
	public Edit edit(String pagetitle, String text, String summary) throws Exception {
		wiki.edit(pagetitle, text, summary);		
		Edit result=new Edit();
		// FIXME - set edit parameters
		return result;
	}

}
