package org.tatrc.act.tokenaccess.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.tatrc.act.tokenaccess.AccessTokenAccess;
import org.tatrc.act.tokenaccess.AccessTokenCreation;
import org.tatrc.act.tokenaccess.AccessTokenRetirement;
import org.tatrc.act.tokenaccess.AccessTokenStatus;
import org.tatrc.act.tokenaccess.beans.AccessToken;

public class AccessTokenRetirementImplTest {
	static private ApplicationContext ctx;
	static private String tokenId;
	static private String subject = "UnitTest-Subject";
	static private String userId1= "ferret@stormwoods.com";
	
	static private String documentId="12345678";
	
	@BeforeClass
	static public void setupTestEnvironment()
	{
		ctx = new ClassPathXmlApplicationContext("org/tatrc/act/tokenaccess/tokenaccess.xml");
		
		//The Database entries created by the test cases should be cleaned up by 
		//the all test run or to the eqv.  To make this easier all entries should have a subject
		//id like UnitTest-Subject.
	}
	
	@Test
	public void testRemoveStaleTokens() {
		AccessTokenCreation createImpl = (AccessTokenCreation) ctx.getBean("tokenFactory");
		AccessTokenAccess accessImpl = (AccessTokenAccess) ctx.getBean("tokenAccess");
		AccessTokenRetirement retirementImpl = (AccessTokenRetirement) ctx.getBean("tokenRetirement");
		AccessToken tok;
		int status;
		tok = createImpl.createSimpleToken(subject, userId1, documentId);
		status = accessImpl.checkAccessTokenStatus(tok.getId(),userId1);
		assertTrue("Access not available to token ("+status+")", status ==AccessTokenStatus.ACCESS_ALLOWED);
		//Set it to have expired a second ago
		tok.setExpirationDate(new Date(System.currentTimeMillis()-1000L));
		createImpl.updateToken(tok);
		status = accessImpl.checkAccessTokenStatus(tok.getId(),userId1);
		assertTrue("Token not showing as expired ("+status+")",status==AccessTokenStatus.EXPIRED);
		retirementImpl.removeStaleTokens();
		status = accessImpl.checkAccessTokenStatus(tok.getId(),userId1);
		assertTrue("Token not removed ("+status+")",status!=AccessTokenStatus.NO_SUCH_TOKEN);
		
	}

	@Test
	public void testRemoveTokenById() {
		AccessTokenCreation createImpl = (AccessTokenCreation) ctx.getBean("tokenFactory");
		AccessTokenAccess accessImpl = (AccessTokenAccess) ctx.getBean("tokenAccess");
		AccessTokenRetirement retirementImpl = (AccessTokenRetirement) ctx.getBean("tokenRetirement");
		AccessToken tok;
		int status;
		tok = createImpl.createSimpleToken(subject, userId1, documentId);
		tokenId=tok.getId();
		retirementImpl.removeToken(tokenId);
		status = accessImpl.checkAccessTokenStatus(tokenId,userId1);
		assertTrue("Token not removed ("+status+")",status==AccessTokenStatus.NO_SUCH_TOKEN);
	}

	@Test
	public void testRetireToken() {
		AccessTokenCreation createImpl = (AccessTokenCreation) ctx.getBean("tokenFactory");
		AccessTokenAccess accessImpl = (AccessTokenAccess) ctx.getBean("tokenAccess");
		AccessTokenRetirement retirementImpl = (AccessTokenRetirement) ctx.getBean("tokenRetirement");
		AccessToken tok;
		int status;
		tok = createImpl.createSimpleToken(subject, userId1, documentId);
		assertNotNull("Token returned null",tok);
		assertNotNull("Token Id returned null",tok.getId());	
		retirementImpl.retireToken(tok.getId());
		status = accessImpl.checkAccessTokenStatus(tok.getId(),userId1);
		assertTrue("Token not showing as expired ("+status+")",status==AccessTokenStatus.EXPIRED);
	}
	@Test
	@Transactional
	public void testRemoveToken() {
		
		AccessTokenCreation createImpl = (AccessTokenCreation) ctx.getBean("tokenFactory");
		AccessTokenAccess accessImpl = (AccessTokenAccess) ctx.getBean("tokenAccess");
		AccessTokenRetirement retirementImpl = (AccessTokenRetirement) ctx.getBean("tokenRetirement");
		AccessToken tok;
		int status;
		tok = createImpl.createSimpleToken(subject, userId1, documentId);
		status = accessImpl.checkAccessTokenStatus(tok.getId(),userId1);
		assertTrue("Access not available to token ("+status+")", status ==AccessTokenStatus.ACCESS_ALLOWED);
		//Set it to have expired a second ago
		retirementImpl.removeToken(tok);
		status = accessImpl.checkAccessTokenStatus(tok.getId(),userId1);
		assertTrue("Token not removed ("+status+")",status==AccessTokenStatus.NO_SUCH_TOKEN);	
	}

}
