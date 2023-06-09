package com.exercise.FinalProject.service;

import com.exercise.FinalProject.exception.DataNotFoundException;
import com.exercise.FinalProject.model.Student;
import com.exercise.FinalProject.model.StudentCourse;
import com.exercise.FinalProject.repository.StudentCourseRepository;
import com.exercise.FinalProject.repository.StudentRepository;
import com.exercise.FinalProject.request.StudentRequest;
import com.exercise.FinalProject.response.StudentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
@Slf4j
public class StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentCourseRepository studentCourseRepository;


    public List<StudentResponse> getAllData() {
        List<StudentResponse> studentResponseList = new ArrayList<>();
        List<Student> studentList = studentRepository.findAll();
        if (CollectionUtils.isEmpty(studentList)) {
            return Collections.emptyList();
        } else {
            for (Student student : studentList) {
                StudentResponse studentResponse = new StudentResponse();
                studentResponse.setStudentId(student.getStudentId());
                studentResponse.setFirstName(student.getFirstName());
                studentResponse.setLastName(student.getLastName());
                studentResponse.setDob(student.getDob());
                studentResponse.setGender(student.getGender());
                studentResponseList.add(studentResponse);
            }
            log.info("Fetch list of all students");
            return studentResponseList;
        }
    }

    public StudentResponse getStudentById(Integer studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        StudentResponse studentResponse = new StudentResponse();
        if (student.isPresent()) {
            log.info("Fetch data by particular student id " + studentId);
            studentResponse.setStudentId(student.get().getStudentId());
            studentResponse.setFirstName(student.get().getFirstName());
            studentResponse.setLastName(student.get().getLastName());
            studentResponse.setDob(student.get().getDob());
            studentResponse.setGender(student.get().getGender());
            return studentResponse;
        } else {
            log.info("Data not found with student id " + studentId);
            throw new DataNotFoundException("Data not found with student id " + studentId);
        }
    }

    public ResponseEntity<Student> save(StudentRequest student) {
        Student duplicateStudent = studentRepository.findByFirstNameAndLastNameAndDobAndGender
                (student.getFirstName(), student.getLastName(), student.getDob(), student.getGender());
        if (ObjectUtils.isEmpty(duplicateStudent)) {
            Student student1 = new Student();
            student1.setFirstName(student.getFirstName());
            student1.setLastName(student.getLastName());
            student1.setDob(student.getDob());
            student1.setGender(student.getGender());
            return new ResponseEntity<>(studentRepository.save(student1), HttpStatus.CREATED);
        } else {
            throw new DataNotFoundException("Same student data is already exist");
        }
    }

    public void deleteData(Integer studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            List<StudentCourse> courseList = studentCourseRepository.findBystudentId(studentId);
            if (courseList.isEmpty()) {
                log.info("Data is not found");
                throw new DataNotFoundException("Data is not found");
            } else {
                studentCourseRepository.deleteAll(courseList);
                studentRepository.delete(student.get());
                log.info("Data is delete successfully with student id " + studentId);
            }
        } else {
            throw new DataNotFoundException("Student id doesn't exist");
        }
    }
}