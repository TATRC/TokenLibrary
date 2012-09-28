package org.tatrc.act.tokenaccess;

import java.util.List;

import org.tatrc.act.tokenaccess.beans.AccessToken;

public interface AccessTokenMaintenace {
	public void grantAccessToToken(String tokenId, String userId);


	public List<AccessToken> findTokensForSubject(String subjectId);

	public AccessToken getTokenById(String tokenId);

	//Note Token Access Audited by a crosscut (AOP)
}
