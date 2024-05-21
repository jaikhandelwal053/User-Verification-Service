package com.user.verification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.verification.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

