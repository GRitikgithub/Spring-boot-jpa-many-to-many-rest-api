package com.exercise.FinalProject.repository;

import com.exercise.FinalProject.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {

    @Query("from Student where lower(firstName) = ?1 and lower(lastName)=?2 and date(dob) = date(?3) and gender=?4")
    Student findByFirstNameAndLastNameAndDobAndGender(String firstName, String lastName, Date dob, Character gender);
}

