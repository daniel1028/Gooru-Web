package org.ednovo.gooru.shared.model.content;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ClassUnitsListDo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public ClassUnitsListDo(){}
	
	private String collectionItemId;
	
	private String status;
	
	private Long associationDate; 
	
	private Integer itemSequence;
	
	private ClassUnitDo resource;
	/**
	 * @return the collectionItemId
	 */
	public String getCollectionItemId() {
		return collectionItemId;
	}

	/**
	 * @param collectionItemId the collectionItemId to set
	 */
	public void setCollectionItemId(String collectionItemId) {
		this.collectionItemId = collectionItemId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the associationDate
	 */
	public Long getAssociationDate() {
		return associationDate;
	}

	/**
	 * @param associationDate the associationDate to set
	 */
	public void setAssociationDate(Long associationDate) {
		this.associationDate = associationDate;
	}

	/**
	 * @return the itemSequence
	 */
	public Integer getItemSequence() {
		return itemSequence;
	}

	/**
	 * @param itemSequence the itemSequence to set
	 */
	public void setItemSequence(Integer itemSequence) {
		this.itemSequence = itemSequence;
	}

	/**
	 * @return the resource
	 */
	public ClassUnitDo getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(ClassUnitDo resource) {
		this.resource = resource;
	}


	
}
