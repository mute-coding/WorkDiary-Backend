package com.rainey.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rainey.project.model.WorkDiary;

@Repository
public interface WorkDiaryRepo extends JpaRepository<WorkDiary, Integer> {

}
