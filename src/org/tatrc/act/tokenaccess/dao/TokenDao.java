package org.tatrc.act.tokenaccess.dao;

import java.util.Date;
import java.util.List;

import org.tatrc.act.tokenaccess.beans.AccessToken;

public interface TokenDao {
	/**
	 * Add a token to the database. The token is assumed to have a unique Id
	 * 
	 * @param token
	 */
	public void addToken(AccessToken token);

	public AccessToken getTokenById(String id);

	public void saveToken(AccessToken token);
	

	public void removeTokenById(String id);

	public void removeToken(AccessToken token);

	public List<AccessToken> getTokensForSubject(String subjectId);

	public List<AccessToken> getTokensForUserId(String userId);
	
	public int RemoveTokensExpiredAsOfDate(Date date);
}
