/**
 * 
 */
package org.tatrc.act.tokenaccess.impl;

import java.util.Iterator;
import java.util.List;

import org.tatrc.act.tokenaccess.AccessTokenCreation;
import org.tatrc.act.tokenaccess.TokenExpirationAgent;
import org.tatrc.act.tokenaccess.beans.AccessToken;
import org.tatrc.act.tokenaccess.dao.TokenDao;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;

/**
 * @author Jerry Goodnough
 * 
 */
public class AccessTokenCreationImpl implements AccessTokenCreation {

	private static NoArgGenerator uuidGenerator;
	private TokenDao dao;
	private TokenExpirationAgent expireAgent;
	
	static 
	{
		EthernetAddress addr = EthernetAddress.fromInterface();
		
        if (addr == null) {
            addr = EthernetAddress.constructMulticastAddress(new java.util.Random(System.currentTimeMillis()));
        }
        uuidGenerator = Generators.timeBasedGenerator(addr);
	}
	
	public AccessTokenCreationImpl(TokenDao dao, TokenExpirationAgent expireAgent)
	{
		this.dao = dao;
		this.expireAgent = expireAgent;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * 
	 * @see
	 * org.tatrc.act.tokenaccess.AccessTokenCreation#createToken(java.lang.String
	 * )
	 */
	@Override
	public AccessToken createToken(String subjectId) {
		AccessToken out = createBaseToken();
		out.setSubject(subjectId);
		this.addToken(out);
		return out;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * 
	 * @see
	 * org.tatrc.act.tokenaccess.AccessTokenCreation#createTokenForDocuementsWithUser(java.lang.String
	 * , java.lang.String, java.util.List)
	 */
	@Override
	public AccessToken createTokenForDocumentsWithUser(String subjectId, String userId,
			List<String> documentIds) {

		AccessToken out = createBaseToken();
		out.setSubject(subjectId);
		out.getGrants().add(userId);

	    Iterator<String> itr = documentIds.iterator();
	    
		for (int i=0;i<documentIds.size();i++)
		{
			String key = String.format("document%d",i+1);
			
			out.getResources().put(key,itr.next());
		}
		this.addToken(out);
		return out;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * 
	 * @see
	 * org.tatrc.act.tokenaccess.AccessTokenCreation#createTokenForDocumentsWithUsers(java.lang.String
	 * , java.util.List, java.util.List)
	 */
	@Override
	public AccessToken createTokenForDocumentsWithUsers(String subjectId,
			List<String> userIds, List<String> documentIds) {

		AccessToken out = createBaseToken();
		out.setSubject(subjectId);
		Iterator<String> usrItr = userIds.iterator();
		
		
		//Save Id's to the Grant set (We wio
		while (usrItr.hasNext())
		{
			out.getGrants().add(usrItr.next());				
		}

	    Iterator<String> itr = documentIds.iterator();
	    
		for (int i=0;i<documentIds.size();i++)
		{
			String key = String.format("document%d",i+1);
			
			out.getResources().put(key,itr.next());
		}
		this.addToken(out);
		return out;
	}




	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tatrc.act.tokenaccess.AccessTokenCreation#createSimpleToken(java.lang.String
	 * , java.lang.String, java.lang.String)
	 */
	@Override
	public AccessToken createSimpleToken(String subjectId, String userId,
			String documentId) {

		AccessToken out = new AccessToken();
		out.setSubject(subjectId);
		out.setId(this.getUUIDAsString());
		out.getGrants().add(userId);
		out.getResources().put("document1",documentId);
		this.addToken(out);
		return out;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tatrc.act.tokenaccess.AccessTokenCreation#updateToken(org.tatrc.act
	 * .tokenaccess.beans.AccessToken)
	 */
	@Override
	public void updateToken(AccessToken token) {
		this.saveToken(token);
		
	}

	private String getUUIDAsString()
	{
		return uuidGenerator.generate().toString();
	}
	
	
	private void addToken(AccessToken token)
	{
	
		dao.addToken(token);
	}
	
	private void saveToken(AccessToken token)
	{
		dao.saveToken(token);
	}

	private AccessToken createBaseToken()
	{
		AccessToken token=new AccessToken();
		token.setId(this.getUUIDAsString());
		//Set Expiration Date
		expireAgent.updateTokenExpiration(token);
		return token;
	}
}
