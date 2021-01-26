package com.bank.web.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bank.web.auth.entity.TokenWithExpiry;

public interface TokenWithExpiryRepository extends JpaRepository<TokenWithExpiry, Long>{

	Optional<TokenWithExpiry> findByUserIdAndIsActive(long userId, Boolean isActive);

	Optional<TokenWithExpiry> findByToken(String token);

	@Modifying
	@Query("UPDATE TokenWithExpiry SET isActive = null WHERE id = ?1")
	int disableToken(long tokenId);

}
