package org.tatrc.act.tokenaccess.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.tatrc.act.tokenaccess.AccessTokenAccess;
import org.tatrc.act.tokenaccess.AccessTokenCreation;
import org.tatrc.act.tokenaccess.AccessTokenMaintenace;
import org.tatrc.act.tokenaccess.AccessTokenStatus;
import org.tatrc.act.tokenaccess.beans.AccessToken;

public class AccessTokenMaintenanceImplTest {
	
	static private ApplicationContext ctx;
	static private String subject = "UnitTest-Subject";
	static private String userId1= "ferret@stormwoods.com";
	static private String userId2= "jerry.goodnough@stormwoods.com";
	static private String documentId="12345678";
	static private String tokenId;
	
	@BeforeClass
	static public void setupTestEnvironment()
	{
		ctx = new ClassPathXmlApplicationContext("org/tatrc/act/tokenaccess/tokenaccess.xml");
		AccessTokenCreation impl = (AccessTokenCreation) ctx.getBean("tokenFactory");
		AccessToken tok = impl.createSimpleToken(subject,userId1,documentId);
		tokenId=tok.getId();
		//The Database entries created by the test cases are cleaned up by 
		//the suite all test run.  To make this easier all entries should have a subject
		//id like UnitTest-Subject. The elements that are cleaned up are identified by the 
		//subjectId of "UnitTest-Subject".
	}
	
	@Test
	@Transactional(readOnly=false)
	public void testGrantAccessToToken() {
		AccessTokenMaintenace maintImpl = (AccessTokenMaintenace) ctx.getBean("tokenMainenance");
		AccessTokenAccess accessImpl = (AccessTokenAccess) ctx.getBean("tokenAccess");
		int status = accessImpl.checkAccessTokenStatus(tokenId,userId2);
		assertTrue("Access should be denied but is not ("+status+")",status == AccessTokenStatus.ACCESS_DENIED );
		maintImpl.grantAccessToToken(tokenId, userId2);
		status = accessImpl.checkAccessTokenStatus(tokenId,userId2);
		assertTrue("Access should be allowed but is not ("+status+")",status == AccessTokenStatus.ACCESS_ALLOWED);
	}

	@Test
	@Transactional(readOnly=true)
	public void testFindTokensForSubject() {
		AccessTokenMaintenace maintImpl = (AccessTokenMaintenace) ctx.getBean("tokenMainenance");
		List<AccessToken> list = maintImpl.findTokensForSubject(subject);
		assertNotNull("Token List null",list);
		assertFalse("Token List empty",list.isEmpty());
	}

	@Test
	@Transactional(readOnly=true)
	public void testGetTokenById() {
		AccessTokenMaintenace maintImpl = (AccessTokenMaintenace) ctx.getBean("tokenMainenance");
		AccessToken token = maintImpl.getTokenById(tokenId);
		assertNotNull("Token null",token);
		assertTrue("Token Id does not match",token.getId().compareTo(tokenId)==0);
	}

}
