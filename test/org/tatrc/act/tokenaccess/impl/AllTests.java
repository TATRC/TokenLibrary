package org.tatrc.act.tokenaccess.impl;

import java.util.Iterator;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.tatrc.act.tokenaccess.AccessTokenHelper;
import org.tatrc.act.tokenaccess.AccessTokenRetirement;
import org.tatrc.act.tokenaccess.beans.AccessToken;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AccessTokenAccessImplTest.class,
		AccessTokenCreationImplTest.class, 
		AccessTokenRetirementImplTest.class,
		AccessTokenHelperImplTest.class,
		AccessTokenMaintenanceImplTest.class,
		SimpleExpirationTokenAgentImplTest.class})
public class AllTests {
	@BeforeClass 
	public static void doOneTimeSetup() {}
    
	@AfterClass 
	@Transactional(readOnly=false)
	public static void doOneTimeTeardown() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("org/tatrc/act/tokenaccess/tokenaccess.xml");
		AccessTokenRetirement retirementImpl = (AccessTokenRetirement) ctx.getBean("tokenRetirement");
		AccessTokenHelper helperImpl = (AccessTokenHelper) ctx.getBean("AccessTokenHelper");

		List<AccessToken> list = helperImpl.findTokensForSubject("UnitTest-Subject");
		Iterator<AccessToken> itr = list.iterator();
		while(itr.hasNext()){
			retirementImpl.removeToken(itr.next());
		}
	}
}
