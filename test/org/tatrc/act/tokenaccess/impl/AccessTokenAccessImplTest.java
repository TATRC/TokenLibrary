package org.tatrc.act.tokenaccess.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tatrc.act.tokenaccess.AccessTokenAccess;
import org.tatrc.act.tokenaccess.AccessTokenCreation;
import org.tatrc.act.tokenaccess.beans.AccessToken;

public class AccessTokenAccessImplTest {
	static private ApplicationContext ctx;
	static private String tokenId;
	static private String subject = "UnitTest-Subject";
	static private String userId1= "ferret@stormwoods.com";
	static private String userId2= "jerry.goodnough@stormwoods.com";
	static private String documentId="12345678";
	
	@BeforeClass
	static public void setupTestEnvironment()
	{
		ctx = new ClassPathXmlApplicationContext("org/tatrc/act/tokenaccess/tokenaccess.xml");
		AccessTokenCreation impl = (AccessTokenCreation) ctx.getBean("tokenFactory");
		AccessToken tok;
		tok = impl.createSimpleToken(subject, userId1, documentId);
		tokenId = tok.getId();
		
		//The Database entries created by the test cases should be cleaned up by 
		//the all test run or to the eqv.  To make this easier all entries should have a subject
		//id like UnitTest-Subject.
	}
	

	@Test
	public void testGetAccessToken() {
		AccessTokenAccess impl = (AccessTokenAccess) ctx.getBean("tokenAccess");
		AccessToken token = impl.getAccessToken(tokenId, userId1);
		assertNotNull("Token not found",token);
		assertEquals("Wrong Token found",token.getId(),tokenId);
		token = impl.getAccessToken(tokenId, userId2);
		assertNull("Token found with ungranted user",token);
	}

	@Test
	public void testCheckAccessTokenStatus() {
		AccessTokenAccess impl = (AccessTokenAccess) ctx.getBean("tokenAccess");
		int i = impl.checkAccessTokenStatus(tokenId, userId1);
		assertTrue("No Access to token available ("+i+")",i>0);
		i = impl.checkAccessTokenStatus(tokenId, userId2);
		assertTrue("Token access in not in the denined status",i == 0);
		i = impl.checkAccessTokenStatus("Undefined", userId1);
		assertTrue("Undefined Token Found",i == -1);
	}
}
