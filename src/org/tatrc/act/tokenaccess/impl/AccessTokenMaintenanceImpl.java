package org.tatrc.act.tokenaccess.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tatrc.act.tokenaccess.AccessTokenMaintenace;
import org.tatrc.act.tokenaccess.beans.AccessToken;
import org.tatrc.act.tokenaccess.dao.TokenDao;

@Service("tokenMainenance")
@Transactional(readOnly = false)
public class AccessTokenMaintenanceImpl implements AccessTokenMaintenace {
	private TokenDao dao;
	@Autowired
	public AccessTokenMaintenanceImpl(TokenDao dao) {
		this.dao = dao;
	}

	@Override
	public void grantAccessToToken(String tokenId, String userId) {
		AccessToken token = dao.getTokenById(tokenId);
		if (token != null) {
			token.getGrants().add(userId);
			dao.saveToken(token);
		} else {
			throw new IllegalArgumentException("No scch token defined");
		}

	}


	@Override
	public List<AccessToken> findTokensForSubject(String subjectId) {
		return dao.getTokensForSubject(subjectId);
	}

	@Override
	@Transactional
	public AccessToken getTokenById(String tokenId) {
		return dao.getTokenById(tokenId);

	}

}
