package org.tatrc.act.tokenaccess;

import java.util.List;

import org.tatrc.act.tokenaccess.beans.AccessToken;

public interface AccessTokenCreation {

	public AccessToken createToken(String subjectId);

	public AccessToken createTokenForDocumentsWithUser(String subjectId, String userId,
			List<String> documentIds);
	
	/**
	 * Create a Token for a list documents and a list of users. Implementation are expected to handle duplicate user Ids.  
	 * 
	 * @param subjectId The Subject of the Token
	 * @param userIds A List of user Ids that will be granted access to this token.
	 * @param documentIds A List o Document Id's tp associate with the token
	 * @return
	 */
	public AccessToken createTokenForDocumentsWithUsers(String subjectId, List<String> userIds,
			List<String> documentIds);


	public AccessToken createSimpleToken(String subjectId, String userId,
			String documentId);

	public void updateToken(AccessToken token);

}
