package org.tatrc.act.tokenaccess.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.tatrc.act.tokenaccess.TokenExpirationAgent;
import org.tatrc.act.tokenaccess.beans.AccessToken;

@Service("tokenExpirationAgent")
public class SimpleExpirationTokenAgentImpl implements TokenExpirationAgent {

	private long timeToExpire = 1000L*86400L*30L; // Default to ~ 30
	// days
	private int mode = 1;

	/**
	 * Set the Mode of the of the Agent
	 *	case 0: No Change to Expiration
	 *	case 1: By Last Access Date
	 *	case 2: By Creation Date
	 * 
	 * @param mode
	 */
	public void setMode(int mode) {
		switch (mode) {
		case 0: // No Change to Expiration
		case 1: // By Last Access Date
		case 2: // By Creation Date
		{
			this.mode = mode;
			break;
		}
		default: {
			throw new IllegalArgumentException("Mode must be between 0,1 or 2");
		}
		}
	}

	public int getMode() {
		return this.mode;
	}

	/**
	 * Time in milliseconds a token is good for
	 * 
	 * @return milliseconds
	 */
	public long getTimeToExpire() {
		return this.timeToExpire;
	}

	/**
	 * Set the time in milliseconds that a token should be extended by
	 * 
	 * @param timeToExpire
	 */
	public void setTimeToExpire(long timeToExpire) {
		this.timeToExpire = timeToExpire;
	}

	@Override
	public boolean isTokenExpired(AccessToken token) {

		Date tokenDate = token.getExpirationDate();
		if (tokenDate != null) {
			Date now = new Date();
			return now.after(token.getExpirationDate());
		} else {
			// No Expiration date is set so it is now expired
			return false;
		}
	}

	@Override
	public void updateTokenExpiration(AccessToken token) {
		if (mode > 0) {
			Date basis;
			switch (mode) {
			case 1: {
				basis = token.getLastAccessDate();
				break;
			}
			case 2: {
				basis = token.getCreationDate();
				break;
			}
			default:
				throw new IllegalStateException("Unknown mode in use");
			}
			Date newExpire = new Date(basis.getTime()
					+ timeToExpire);
			token.setExpirationDate(newExpire);
		}
	}

}
