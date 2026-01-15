package com.rainey.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rainey.project.model.ProjectName;

@Repository
public interface ProjectNameRepo extends JpaRepository<ProjectName, Integer> {

}
