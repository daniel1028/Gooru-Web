
/**
 * 
*/
package org.ednovo.gooru.shared.model.content;

import java.io.Serializable;
import java.util.List;


/**
 * @fileName : MetaDO.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 05-Jun-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer: 
 */
public class MetaDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5352082220908500490L;
	/**
	 * 
	 */

	//private Array permissions;
	//private String[] permissions;
	private List<String> permissions;
	private Integer collaboratorCount;
	private boolean isCollaborator;
	
	public MetaDO(){}
	
	
	/** 
	 * This method is to get the collaboratorCount
	 */
	public Integer getCollaboratorCount() {
		return collaboratorCount;
	}

	/** 
	 * This method is to set the collaboratorCount
	 */
	public void setCollaboratorCount(Integer collaboratorCount) {
		this.collaboratorCount = collaboratorCount;
	}

	/** 
	 * This method is to get the isCollaborator
	 */
	public boolean isIsCollaborator() {
		return isCollaborator;
	}

	/** 
	 * This method is to set the isCollaborator
	 */
	public void setCollaborator(boolean isCollaborator) {
		this.isCollaborator = isCollaborator;
	}

	/** 
	 * This method is to get the permissions
	 */
	public List<String> getPermissions() {
		return permissions;
	}

	/** 
	 * This method is to set the permissions
	 */
	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
}
