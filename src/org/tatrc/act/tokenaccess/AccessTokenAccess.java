package org.tatrc.act.tokenaccess;

import org.tatrc.act.tokenaccess.beans.AccessToken;

public interface AccessTokenAccess {

	public AccessToken getAccessToken(String tokenId, String userId);

	public int checkAccessTokenStatus(String tokenId, String userId);
}
