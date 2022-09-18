package com.skywalker.reddit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skywalker.reddit.entity.VerificationToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
    Optional<VerificationToken> findByToken(String s);

}
