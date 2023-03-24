package com.exercise.FinalProject.repository;

import com.exercise.FinalProject.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    @Query("from Course where  lower(name)=?1")
    Course duplicateCourse(String name);

}
