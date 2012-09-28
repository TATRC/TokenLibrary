package org.tatrc.act.tokenaccess;

import java.util.List;

import org.tatrc.act.tokenaccess.beans.AccessToken;

public interface AccessTokenHelper {

	public AccessToken createTokenForDocuments(String subjectId, String userId,
			List<String> documentIds);

	public List<String> getDocumentIdsForToken(AccessToken token);

	public List<AccessToken> findTokensForSubject(String subjectId);

	public List<AccessToken> findTokensGrantedToUser(String userId);

}
