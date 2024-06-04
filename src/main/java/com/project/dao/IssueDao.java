package com.project.dao;

import com.project.entities.Issues;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssueDao extends JpaRepository<Issues,Long> {

    Optional<Issues> findById(long id);

    List<Issues> findByProjectID(long projectId);
}
