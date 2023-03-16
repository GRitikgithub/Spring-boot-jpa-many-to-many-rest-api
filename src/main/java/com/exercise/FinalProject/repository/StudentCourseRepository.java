package com.exercise.FinalProject.repository;

import com.exercise.FinalProject.model.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse,Integer> {
    List<StudentCourse> findByCourseId(Integer courseId);

    List<StudentCourse> findBystudentId(Integer studentId);

    Optional<StudentCourse> findByStudentIdAndCourseId(Integer studentId, Integer courseId);
}
