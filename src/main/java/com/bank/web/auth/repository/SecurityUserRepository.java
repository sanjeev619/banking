package com.bank.web.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.bank.web.auth.entity.SecurityUser;

public interface SecurityUserRepository extends JpaRepository<SecurityUser, Long>{

	@Modifying
	@Query("UPDATE SecurityUser SET isCredentialsNonExpired = false WHERE id = ?1")
	int disableUser(long securityUserId);

	Optional<UserDetails> findByUsername(String username);

}
