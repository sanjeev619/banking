package com.bank.web.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bank.web.auth.entity.SecurityUser;

public interface SecurityUserRepository extends JpaRepository<SecurityUser, Long>{

	@Modifying
	@Query("UPDATE SecurityUser SET isDisabled = true WHERE id = ?1")
	int disableUser(long securityUserId);

}
