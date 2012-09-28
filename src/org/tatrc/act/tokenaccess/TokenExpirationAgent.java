package org.tatrc.act.tokenaccess;

import org.tatrc.act.tokenaccess.beans.AccessToken;

public interface TokenExpirationAgent {
	
	public boolean isTokenExpired(AccessToken token);
	public void updateTokenExpiration(AccessToken token);

}
