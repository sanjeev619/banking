package com.bank.web.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper=false)
public abstract class AuditableEntity <T extends Serializable> extends AbstractPersistable<T>{
	
	private Long createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;
	
	@PrePersist
	protected void onCreate() {
		if (createdOn == null)
			createdOn = new Date();
		if (updatedOn == null)
			updatedOn = new Date();
	}

    @PreUpdate
    protected void onUpdate() {
    		updatedOn = new Date();
    }
    
    public Long getCreatedBy() {
    	if(createdBy == null)
    		return 0L;
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
    
    public Date getCreatedOn() {
		return createdOn;
	}
	
    public Long getUpdatedBy() {
    	if(updatedBy == null)
    		return 0L;
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setCreatedOn(Date date) {
		this.createdOn = date;
	}
	
	public void setUpdatedOn(Date date) {
		this.updatedOn = date;
	}
}
