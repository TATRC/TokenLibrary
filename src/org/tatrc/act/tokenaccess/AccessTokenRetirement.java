package org.tatrc.act.tokenaccess;

import org.tatrc.act.tokenaccess.beans.AccessToken;

public interface AccessTokenRetirement {

	/**
	 * Removes Expired Tokens
	 */
	public void removeStaleTokens();

	/**
	 * Remove a token from the database
	 * @param tokenId
	 */
	public void removeToken(String tokenId);

	/**
	 * Remove a token from the database
	 * @param token
	 */
	public void removeToken(AccessToken token);
	/**
	 * Retires a token by making it expired. 
	 * @param tokenId 
	 */
	public void retireToken(String tokenId);
	
	
}
