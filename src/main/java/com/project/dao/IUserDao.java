package com.project.dao;

import com.project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserDao extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Optional<User> findById(Long id);

}
