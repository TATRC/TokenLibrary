package org.tatrc.act.tokenaccess.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.tatrc.act.tokenaccess.AccessTokenAccess;
import org.tatrc.act.tokenaccess.AccessTokenStatus;
import org.tatrc.act.tokenaccess.TokenExpirationAgent;
import org.tatrc.act.tokenaccess.beans.AccessToken;
import org.tatrc.act.tokenaccess.dao.TokenDao;

@Service("tokenAccess")
public class AccessTokenAccessImpl implements AccessTokenAccess {
	private TokenDao dao;
	private TokenExpirationAgent expireAgent;
	
	@Autowired
	public AccessTokenAccessImpl(TokenDao dao, TokenExpirationAgent expireAgent)
	{
		this.dao = dao;
		this.expireAgent = expireAgent;
	}
	
	@Override
	@Transactional(readOnly=false)
	public AccessToken getAccessToken(String tokenId, String userId) {
		AccessToken tok = dao.getTokenById(tokenId);
		if ((tok != null) && (tok.getGrants().contains(userId)))
		{
			Date now = new Date();
			//No access is granted to this user for this token
			//Should we audit ?
			//A Cross cut could detect the state and audit as required
			//Here we update the Last Access Date
			if (expireAgent.isTokenExpired(tok))
			{
				//We have an expired token
				return null;
			}
			//Update the access date first
			tok.setLastAccessDate(now);
			// Check policy of update for the token expiration and update the expiration date as required.
			expireAgent.updateTokenExpiration(tok);
			dao.saveToken(tok);
			return tok;
			
		}
		else
		{
			return null;
		}
		
	}

	@Override
	@Transactional(readOnly=true)
	public int checkAccessTokenStatus(String tokenId, String userId) {

		AccessToken tok = dao.getTokenById(tokenId);
		if (tok == null){
			return AccessTokenStatus.NO_SUCH_TOKEN;  //Undefined
		}
		if (expireAgent.isTokenExpired(tok))
		{
			return AccessTokenStatus.EXPIRED;  //Expired
		}
		return tok.getGrants().contains(userId)?AccessTokenStatus.ACCESS_ALLOWED:AccessTokenStatus.ACCESS_DENIED;  //No Access : Access
	}

}
