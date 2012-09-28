/**
 * 
 */
package org.tatrc.act.tokenaccess.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tatrc.act.tokenaccess.beans.AccessToken;

/**
 * @author Jerry Goodnough
 *
 */
@Transactional(readOnly = false)
@Repository("tokenDAO")
public class HibernateTokenDao implements TokenDao {

	private SessionFactory sessionFactory;
	@Autowired
	public HibernateTokenDao(SessionFactory sessionFactory)
	{
		this.sessionFactory=sessionFactory;
	}
	private Session currentSession()
	{
		return sessionFactory.getCurrentSession();
	}
	/* (non-Javadoc)
	 * @see org.tatrc.act.tokenaccess.dao.TokenDao#addToken(org.tatrc.act.tokenaccess.beans.AccessToken)
	 */
	@Override
	@Transactional()
	public void addToken(AccessToken token) {
		
		currentSession().save(token);
	}

	/* (non-Javadoc)
	 * @see org.tatrc.act.tokenaccess.dao.TokenDao#getTokenById(java.lang.String)
	 */
	@Override
	public AccessToken getTokenById(String id) {
		
		return (AccessToken) currentSession().get(AccessToken.class,id);
	}

	/* (non-Javadoc)
	 * @see org.tatrc.act.tokenaccess.dao.TokenDao#saveToken(org.tatrc.act.tokenaccess.beans.AccessToken)
	 */
	@Override
	public void saveToken(AccessToken token) {
		
		currentSession().update(token);
	}


	/* (non-Javadoc)
	 * @see org.tatrc.act.tokenaccess.dao.TokenDao#removeTokenById(java.lang.String)
	 */
	@Override
	public void removeTokenById(String id) {
		
		AccessToken token = getTokenById(id);
		currentSession().delete(token);
	}

	/* (non-Javadoc)
	 * @see org.tatrc.act.tokenaccess.dao.TokenDao#removeToken(org.tatrc.act.tokenaccess.beans.AccessToken)
	 */
	@Override
	public void removeToken(AccessToken token) {
		currentSession().delete(token);

	}

	/* (non-Javadoc)
	 * @see org.tatrc.act.tokenaccess.dao.TokenDao#getTokensForSubject(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AccessToken> getTokensForSubject(String subjectId) {
		Query qry = currentSession().createQuery("from AccessToken a where a.subject = :subject").setString("subject",subjectId);

		List<AccessToken> list = (List<AccessToken>) qry.list();
		return list;
	}
	/*
	 * (non-Javadoc)
	 * @see org.tatrc.act.tokenaccess.dao.TokenDao#getTokensForUserId(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AccessToken> getTokensForUserId(String userId) {
		String query = "SELECT a FROM AccessToken a, IN(a.grants) s WHERE s = :userId";
		Query qry = currentSession().createQuery(query).setString("userId",userId);

		List<AccessToken> list = (List<AccessToken>) qry.list();
		return list;
	}
	/*
	 * (non-Javadoc)
	 * @see org.tatrc.act.tokenaccess.dao.TokenDao#RemoveTokensExpiredAsOfDate(java.util.Date)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public int RemoveTokensExpiredAsOfDate(Date date) {
		String hqlDelete = "from AccessToken a where a.expirationDate <= :date";
		Query qry = currentSession().createQuery( hqlDelete )
		        .setDate( "date", date );
		List<AccessToken> list = (List<AccessToken>) qry.list();
		Iterator<AccessToken> itr = list.iterator();
		int out = list.size();
		while(itr.hasNext())
		{
			currentSession().delete(itr.next());
		}
		return out;
	}

}
