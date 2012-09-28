package org.tatrc.act.tokenaccess.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tatrc.act.tokenaccess.AccessTokenRetirement;
import org.tatrc.act.tokenaccess.beans.AccessToken;
import org.tatrc.act.tokenaccess.dao.TokenDao;
@Service("tokenRetirement")
@Transactional(readOnly=false)
public class AccessTokenRetirementImpl implements AccessTokenRetirement {
	private TokenDao dao;
	@Autowired
	public AccessTokenRetirementImpl(TokenDao dao) {
		this.dao = dao;
	}

	@Override
	public void removeStaleTokens() {
		//For now we define a stable token as one that is expired
		Date now = new Date();
		dao.RemoveTokensExpiredAsOfDate(now);
	}

	@Override
	public void removeToken(String tokenId) {
		dao.removeTokenById(tokenId);

	}

	@Override
	public void retireToken(String tokenId) {
		Date now = new Date();
		AccessToken token = dao.getTokenById(tokenId);
		if (token!=null )
		{
			if (token.getExpirationDate()== null)
			{
				token.setExpirationDate(now);
				dao.saveToken(token);
			}
			else if (token.getExpirationDate().after(now))
			{
				token.setExpirationDate(now);
				dao.saveToken(token);
			}
		}
	}

	@Override
	public void removeToken(AccessToken token) {
		dao.removeToken(token);
		
	}

}
