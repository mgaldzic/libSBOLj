package org.sbolstandard.core2;

import static org.sbolstandard.core2.URIcompliance.createCompliantURI;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Zhen Zhang
 * @author Nicholas Roehner
 * @author Chris Myers
 * @version 2.1
 */

public class Collection extends TopLevel{

	private Set<URI> members;

	Collection(URI identity) throws SBOLValidationException {
		super(identity);
		this.members = new HashSet<>();
	}

	private Collection(Collection collection) throws SBOLValidationException {
		//super(collection.getIdentity());
		super(collection);
		this.members = new HashSet<>();
		Set<URI> newMembers = new HashSet<>();
		for (URI member : collection.getMemberURIs()) {
			newMembers.add(member);
		}
		this.setMembers(newMembers);
	}

	/**
	 * Adds the given member URI to this Collection object's
	 * set of reference member URIs.
	 *
	 * @param memberURI References to a TopLevel object
	 * @return {@code true} if the matching member reference has been added successfully,
	 *         {@code false} otherwise.
	 * @throws SBOLValidationException violates validation rule
	 */
	public boolean addMember(URI memberURI) throws SBOLValidationException {
		if (sbolDocument != null && sbolDocument.isComplete()) {
			if (sbolDocument.getTopLevel(memberURI)==null) {
				throw new SBOLValidationException("sbol-12103", this);
			}
		}
		return members.add(memberURI);
	}

	/**
	 * Removes the given member reference from the set of member references.
	 * <p>
	 * If this Collection object belongs to an SBOLDocument instance, then
	 * the SBOLDcouement instance
	 * is checked for compliance first. Only a compliant SBOLDocument instance
	 * is allowed to be edited.
	 *
	 * @param memberURI the reference to a TopLevel object to be removed from the SBOL Document.
	 * @return {@code true} if the matching member reference is removed successfully,
	 *         {@code false} otherwise.
	 */
	public boolean removeMember(URI memberURI) {
		return members.remove(memberURI);
	}

	/**
	 * Clears the existing set of member references first, then adds the given
	 * set of the member references to this Collection object.
	 * <p>
	 * If this Collection object belongs to an SBOLDocument instance, then
	 * the SBOLDcouement instance
	 * is checked for compliance first. Only a compliant SBOLDocument instance
	 * is allowed to be edited.
	 *
	 * @param members A set of URI references to zero or more TopLevel objects within the SBOL Document.
	 * @throws SBOLValidationException violates validation rule
	 */
	public void setMembers(Set<URI> members) throws SBOLValidationException {
		clearMembers();
		for (URI member : members) {
			addMember(member);
		}
	}

	/**
	 * Returns the set of member URIs referenced by this Collection object.
	 *
	 * @return the set of member URIs referenced by this Collection object.
	 */
	public Set<URI> getMemberURIs() {
		Set<URI> result = new HashSet<>();
		result.addAll(members);
		return result;
	}

	/**
	 * Returns the set of Member instances referenced by this Collection object.
	 *
	 * @return the set of Member instances referenced by this Collection object.
	 */
	public Set<TopLevel> getMembers() {
		Set<TopLevel> result = new HashSet<>();
		for (URI memberURI : members) {
			TopLevel member = sbolDocument.getTopLevel(memberURI);
			result.add(member);
		}
		return result;
	}

	/**
	 * Checks if the given member URI is included in this Collection
	 * object's set of reference member URIs.
	 *
	 * @param memberURI The URI that references to a TopLevel object within the SBOL Document
	 * @return {@code true} if this set contains the given URI.
	 */
	public boolean containsMember(URI memberURI) {
		return members.contains(memberURI);
	}

	/**
	 * Removes all entries of this Collection object's set of reference
	 * member URIs. The set will be empty after this call returns.
	 * <p>
	 * If this Collection object belongs to an SBOLDocument instance,
	 * then the SBOLDcouement instance is checked for compliance first. Only a compliant SBOLDocument instance
	 * is allowed to be edited.
	 *
	 */
	public void clearMembers() {
		members.clear();
	}

	/* (non-Javadoc)
	 * @see org.sbolstandard.core2.abstract_classes.Documented#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((members == null) ? 0 : members.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see org.sbolstandard.core2.abstract_classes.Documented#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Collection other = (Collection) obj;
		if (members == null) {
			if (other.members != null)
				return false;
		} else if (!members.equals(other.members))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see org.sbolstandard.core2.abstract_classes.TopLevel#deepCopy()
	 */
	@Override
	protected Collection deepCopy() throws SBOLValidationException {
		return new Collection(this);
	}

	/* (non-Javadoc)
	 * @see org.sbolstandard.core2.abstract_classes.TopLevel#copy(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	Collection copy(String URIprefix, String displayId, String version) throws SBOLValidationException {
		Collection cloned = this.deepCopy();
		cloned.setPersistentIdentity(createCompliantURI(URIprefix,displayId,""));
		cloned.setDisplayId(displayId);
		cloned.setVersion(version);
		URI newIdentity = createCompliantURI(URIprefix,displayId,version);
		if (!this.getIdentity().equals(newIdentity)) {
			cloned.setWasDerivedFrom(this.getIdentity());
		} else {
			cloned.setWasDerivedFrom(this.getWasDerivedFrom());
		}
		cloned.setIdentity(newIdentity);
		return cloned;
	}

	/* (non-Javadoc)
	 * @see org.sbolstandard.core2.abstract_classes.TopLevel#checkDescendantsURIcompliance()
	 */
	@Override
	protected void checkDescendantsURIcompliance() throws SBOLValidationException {
		URIcompliance.isTopLevelURIformCompliant(this.getIdentity());
	}

	@Override
	public String toString() {
		return "Collection ["
				+ "identity=" + identity 
				+ (this.isSetDisplayId()?", displayId=" + displayId:"") 
				+ (this.isSetName()?", name=" + name:"")
				+ (this.isSetDescription()?", description=" + description:"") 
				+ (members.size()>0?", members=" + members:"")  
				+ "]";
	}

}
