package com.steve.paymybuddy.dao;

import com.steve.paymybuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    Optional<User> findById(Integer id);
    boolean existsByEmail(String email);
}
