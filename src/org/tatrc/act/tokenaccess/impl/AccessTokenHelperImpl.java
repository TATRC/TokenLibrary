package org.tatrc.act.tokenaccess.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tatrc.act.tokenaccess.AccessTokenCreation;
import org.tatrc.act.tokenaccess.AccessTokenHelper;
import org.tatrc.act.tokenaccess.beans.AccessToken;
import org.tatrc.act.tokenaccess.dao.TokenDao;
@Service("accessTokenHelper")
public class AccessTokenHelperImpl implements AccessTokenHelper {
	
	private TokenDao dao;
	private AccessTokenCreation tokCreator;
	@Autowired
	public AccessTokenHelperImpl(TokenDao dao,AccessTokenCreation tokCreator)
	{
		this.dao = dao;
		this.tokCreator = tokCreator;
	}
	@Override
	@Transactional(readOnly=false)	
	public AccessToken createTokenForDocuments(String subjectId, String userId,
			List<String> documentIds) {
		return  tokCreator.createTokenForDocumentsWithUser(subjectId, userId, documentIds);
	}

	@Override
	public List<String> getDocumentIdsForToken(AccessToken token) {
		
		LinkedList<String> out = new LinkedList<String>();
		Map<String,String> map = token.getResources();
		Iterator<String> itr = map.keySet().iterator();
		while(itr.hasNext())
		{
			String key = itr.next();
			if (key.startsWith("document"))
			{
				out.add(map.get(key));
			}
		}
		return out;
	}


	@Override
	@Transactional(readOnly=false)
	public List<AccessToken> findTokensForSubject(String subjectId) {
		return dao.getTokensForSubject(subjectId);
	}

	@Override
	@Transactional(readOnly=false)
	public List<AccessToken> findTokensGrantedToUser(String userId) {
		return dao.getTokensForUserId(userId);
	}

}
