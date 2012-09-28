/**
 * 
 */
package org.tatrc.act.tokenaccess.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;






/**
 * Core Domain Bean for an Access Token
 * May be lazy loaded via the ORM layer
 * 
 * @author Jerry Goodnough
 * 
 */
@Entity
@Table(name = "ACCESSTOKENS")
public class AccessToken {

	private String id;
	private String subject;
	private Date creationDate;
	private Date lastAccessDate;
	private Date expirationDate;

	private Set<String> grants;
	private Map<String, String> resources;
	private Map<String, Serializable> objectResources;

	public AccessToken() {
		creationDate = new Date();
		lastAccessDate = creationDate;
		grants = new HashSet<String>();
		resources = new HashMap<String, String>();
		objectResources = new HashMap<String, Serializable>();

	}

	public Date getCreationDate() {
		return this.creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate=creationDate;
	}

	public Date getLastAccessDate() {
		return this.lastAccessDate;
	}

	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;

	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;

	}
	
	@Id
	/**
	 * The Id of the token - a UUID - In a pure Hibernate Implementation with MySQL 
	 * we could actually uses a UUID generator, but since would tie the implementation to 
	 * MySQL and Hiberbate we have relied on generating the UUID ourselves.
	 * 
	 * @return
	 */
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	@ElementCollection(fetch = FetchType.EAGER)
	public Set<String> getGrants() {
		return grants;
	}

	public void setGrants(Set<String> grants) {
		this.grants = grants;
	}
	@ElementCollection(fetch = FetchType.EAGER)
	public Map<String, String> getResources() {
		return resources;
	}

	public void setResources(Map<String, String> resources) {
		this.resources = resources;
	}
	@ElementCollection(fetch = FetchType.EAGER)
	public Map<String, Serializable> getObjectResources() {
		return objectResources;
	}

	public void setObjectResources(Map<String, Serializable> objectResources) {
		this.objectResources = objectResources;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result
				+ ((expirationDate == null) ? 0 : expirationDate.hashCode());
		result = prime * result + ((grants == null) ? 0 : grants.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastAccessDate == null) ? 0 : lastAccessDate.hashCode());
		result = prime * result
				+ ((objectResources == null) ? 0 : objectResources.hashCode());
		result = prime * result
				+ ((resources == null) ? 0 : resources.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccessToken other = (AccessToken) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (expirationDate == null) {
			if (other.expirationDate != null)
				return false;
		} else if (!expirationDate.equals(other.expirationDate))
			return false;
		if (grants == null) {
			if (other.grants != null)
				return false;
		} else if (!grants.equals(other.grants))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastAccessDate == null) {
			if (other.lastAccessDate != null)
				return false;
		} else if (!lastAccessDate.equals(other.lastAccessDate))
			return false;
		if (objectResources == null) {
			if (other.objectResources != null)
				return false;
		} else if (!objectResources.equals(other.objectResources))
			return false;
		if (resources == null) {
			if (other.resources != null)
				return false;
		} else if (!resources.equals(other.resources))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AccessToken [id=" + id + ", subject=" + subject
				+ ", creationDate=" + creationDate + ", lastAccessDate="
				+ lastAccessDate + ", expirationDate=" + expirationDate
				+ ", grants=" + grants + ", resources=" + resources
				+ ", objectResources=" + objectResources + "]";
	}

}
