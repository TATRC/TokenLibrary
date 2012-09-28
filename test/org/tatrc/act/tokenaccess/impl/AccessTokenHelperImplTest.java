package org.tatrc.act.tokenaccess.impl;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tatrc.act.tokenaccess.AccessTokenCreation;
import org.tatrc.act.tokenaccess.AccessTokenHelper;
import org.tatrc.act.tokenaccess.beans.AccessToken;

public class AccessTokenHelperImplTest {

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
		
		//The Database entries created by the test cases should be cleaned up by 
		//the all test run or to the eqv.  To make this easier all entries should have a subject
		//id like UnitTest-Subject.
	}
	

	@Test
	public void testCreateTokenForDocuments() {
		AccessTokenHelper impl = (AccessTokenHelper) ctx.getBean("accessTokenHelper");
		LinkedList<String> list = new LinkedList<String>();
		list.add("223333333");
		list.add("88858533");
		list.add(documentId);
		AccessToken token = impl.createTokenForDocuments(subject, userId1, list);
		assertNotNull("Token is null",token);
		assertTrue("Token does n not have the correct number of documents",token.getResources().containsKey("document3"));
		assertTrue("Document 3 is not the expected document ",token.getResources().get("document3").compareTo(documentId)==0);
		
	}

	@Test
	public void testGetDocumentIdsForToken() {
		AccessTokenHelper impl = (AccessTokenHelper) ctx.getBean("accessTokenHelper");
		LinkedList<String> list = new LinkedList<String>();
		list.add("223333333");
		list.add("88858533");
		list.add(documentId);
		AccessToken token = impl.createTokenForDocuments(subject, userId1, list);
		assertNotNull("Token is null",token);
		assertTrue("Token does n not have the correct number of documents",token.getResources().containsKey("document3"));
		assertTrue("Document 3 is not the expected document ",token.getResources().get("document3").compareTo(documentId)==0);
		
		List<String> docs = impl.getDocumentIdsForToken(token);
		assertNotNull("Doucment List is Empty", docs);
		assertTrue("Document List size is different from original ("+docs.size()+")",docs.size()==list.size());
		assertTrue("List does not contain an expected document",docs.contains(documentId));
	}

	@Test
	public void testFindTokensForSubject() {
		AccessTokenHelper impl = (AccessTokenHelper) ctx.getBean("accessTokenHelper");
		LinkedList<String> list = new LinkedList<String>();
		list.add("223333333");
		list.add("88858533");
		list.add(documentId);
		AccessToken token = impl.createTokenForDocuments(subject, userId1, list);
		assertNotNull("Token is null",token);
		List<AccessToken> tokenList = impl.findTokensForSubject(subject);
		assertNotNull("Search List result is Null",tokenList);
		assertTrue("No documents found",tokenList.size()>0);
		tokenList = impl.findTokensForSubject("JUNIT-NonExistent Subject");
		assertNotNull("Search List result is Null - Non Existend ",tokenList);
		assertTrue("Documents found for non existent subject",tokenList.size()==0);
		
	}

	@Test
	public void testFindTokensGrantedToUser() {
		AccessTokenHelper impl = (AccessTokenHelper) ctx.getBean("accessTokenHelper");
		LinkedList<String> list = new LinkedList<String>();
		list.add("223333333");
		list.add("88858533");
		list.add(documentId);
		AccessToken token = impl.createTokenForDocuments(subject, userId1, list);
		assertNotNull("Token is null",token);
		List<AccessToken> tokenList = impl.findTokensGrantedToUser(userId1);
		assertNotNull("Search List result is Null",tokenList);
		assertTrue("No documents found",tokenList.size()>0);
		//System.out.println("Found "+tokenList.size()+" documents for user "+userId1);
		tokenList = impl.findTokensGrantedToUser("JUNIT-NonExistent User");
		assertNotNull("Search List result is Null - Non Existend ",tokenList);
		assertTrue("Documents found for non existent subject",tokenList.size()==0);
	}

}
