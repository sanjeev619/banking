package com.bank.web.auth.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.bank.web.base.AuditableEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "isActive"}))
public class TokenWithExpiry extends AuditableEntity<Long>{
	
	public TokenWithExpiry(long userId) {
		this.userId = userId;
		this.token = UUID.randomUUID().toString();
		this.isActive = true;
	}
	
	private long userId;
	@Column(unique = true)
	private String token;
	private Boolean isActive;
}
