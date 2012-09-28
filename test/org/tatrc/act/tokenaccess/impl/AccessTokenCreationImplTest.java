package org.tatrc.act.tokenaccess.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tatrc.act.tokenaccess.AccessTokenCreation;
import org.tatrc.act.tokenaccess.beans.AccessToken;



public class AccessTokenCreationImplTest {

	@Test
	public void testCreateTokenWithSubject() {
		 ApplicationContext ctx = new ClassPathXmlApplicationContext("org/tatrc/act/tokenaccess/tokenaccess.xml");
		 AccessTokenCreation impl = (AccessTokenCreation) ctx.getBean("tokenFactory");
		 String subject = "UnitTest-Subject";
		 AccessToken tok = impl.createToken(subject);
		 checkToken(tok);
		 assertTrue("Token Subject does not match",tok.getSubject().compareTo(subject)==0);
		 System.out.println("Check UUID = "+tok.getId());
	}

	@Test
	public void testCreateSimpleToken() {
		 ApplicationContext ctx = new ClassPathXmlApplicationContext("org/tatrc/act/tokenaccess/tokenaccess.xml");
		 AccessTokenCreation impl = (AccessTokenCreation) ctx.getBean("tokenFactory");
		 String subject = "UnitTest-Subject";
		 String user= "ferret@stormwoods.com";
		 String document = "12345678";
		 AccessToken tok = impl.createSimpleToken(subject,user,document);
		 checkToken(tok);
		 assertTrue("Token Subject does not match",tok.getSubject().compareTo(subject)==0);
		 assertNotNull("Token Grants is null",tok.getGrants());
		 assertTrue("Token Grant does not contain the user",tok.getGrants().contains(user));
		 assertNotNull("Token Resources is null",tok.getResources());
		 assertTrue("Token Resources does not contain the document1",tok.getResources().containsKey("document1"));
		 
	}


	@Test
	public void testCreateTokenForDocumentsWithUsers() {
		
		 ApplicationContext ctx = new ClassPathXmlApplicationContext("org/tatrc/act/tokenaccess/tokenaccess.xml");
		 AccessTokenCreation impl = (AccessTokenCreation) ctx.getBean("tokenFactory");
		 String subject = "UnitTest-Subject";
		 LinkedList<String> users = new LinkedList<String>();
		 LinkedList<String> docs = new LinkedList<String>();
		 String user1= "ferret@stormwoods.com";
		 String user2= "jerry.goodnough@stormwoods.com";
		 users.add(user1);
		 users.add(user2);	 
		 String doc1="12345678";
		 docs.add(doc1);
		 String doc2="87654321";
		 docs.add(doc2);
		 String doc3="12344321";
		 docs.add(doc3);
		 AccessToken tok = impl.createTokenForDocumentsWithUsers(subject, users, docs);
		 checkToken(tok);
		 assertTrue("Token Subject does not match",tok.getSubject().compareTo(subject)==0);
		 assertNotNull("Token Grants is null",tok.getGrants());
		 assertTrue("Token Grant does not contain the user",tok.getGrants().contains(user1));
		 assertTrue("Token Grant does not contain the user",tok.getGrants().contains(user2));
		 assertNotNull("Token Resources is null",tok.getResources());
		 assertTrue("Saved document total mismatch",tok.getResources().size()==docs.size());
		 if(tok.getResources().containsKey("document3")==false)
		 {
			 Set<String> rsc = tok.getResources().keySet();
			 Iterator<String> itr = rsc.iterator();
			 while(itr.hasNext())
			 {
				 System.out.println("Check ResourceKey = "+itr.next());		 
			 }
		 }
		 assertTrue("Token Resources does not contain the document1",tok.getResources().containsKey("document1"));
		 assertTrue("Token Resources does not contain the document2",tok.getResources().containsKey("document2"));
		 assertTrue("Token Resources does not contain the document3",tok.getResources().containsKey("document3"));
		 assertTrue("Document 3 does to have the expected id",tok.getResources().get("document3").compareTo(doc3)==0);
	}

	@Test
	public void testCreateTokenForDocuementsWithUser() {
		 ApplicationContext ctx = new ClassPathXmlApplicationContext("org/tatrc/act/tokenaccess/tokenaccess.xml");
		 AccessTokenCreation impl = (AccessTokenCreation) ctx.getBean("tokenFactory");
		 String subject = "UnitTest-Subject";
		 LinkedList<String> docs = new LinkedList<String>();
		 String user1= "ferret@stormwoods.com";
		 String doc1="12345678";
		 docs.add(doc1);
		 String doc2="87654321";
		 docs.add(doc2);
		 String doc3="12344321";
		 docs.add(doc3);
		 AccessToken tok = impl.createTokenForDocumentsWithUser(subject, user1, docs);
		 checkToken(tok);
		 assertTrue("Token Subject does not match",tok.getSubject().compareTo(subject)==0);
		 assertNotNull("Token Grants is null",tok.getGrants());
		 assertTrue("Token Grant does not contain the user",tok.getGrants().contains(user1));
		 assertNotNull("Token Resources is null",tok.getResources());
		 assertTrue("Saved document total mismatch",tok.getResources().size()==docs.size());
		 if(tok.getResources().containsKey("document3")==false)
		 {
			 Set<String> rsc = tok.getResources().keySet();
			 Iterator<String> itr = rsc.iterator();
			 while(itr.hasNext())
			 {
				 System.out.println("Check ResourceKey = "+itr.next());		 
			 }
		 }
		 assertTrue("Token Resources does not contain the document1",tok.getResources().containsKey("document1"));
		 assertTrue("Token Resources does not contain the document2",tok.getResources().containsKey("document2"));
		 assertTrue("Token Resources does not contain the document3",tok.getResources().containsKey("document3"));
		 assertTrue("Document 3 does to have the expected id",tok.getResources().get("document3").compareTo(doc3)==0);
	}

	@Test
	@Ignore
	public void testUpdateToken() {
		
		fail("Not yet implemented");
		
		//Create a new token
		//Alter token fields
		//Save the token
		//Fetch the token
		//Verify the change
	}
	
	protected void checkToken(AccessToken tok)
	{
		 assertNotNull("Token is null",tok);
		 assertNotNull("Token Subject is null",tok.getSubject());
		 assertNotNull("Token Id is null",tok.getId());

	}
}
