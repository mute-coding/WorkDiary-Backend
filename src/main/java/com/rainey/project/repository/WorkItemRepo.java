package com.rainey.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rainey.project.model.WorkDiary;
import com.rainey.project.model.WorkItem;

@Repository
public interface WorkItemRepo extends JpaRepository<WorkItem, Integer> {

}
