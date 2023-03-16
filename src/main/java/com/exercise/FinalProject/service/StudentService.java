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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        if(CollectionUtils.isEmpty(studentList)){
            return null;
        }else{
            for(Student student: studentList){
                StudentResponse studentResponse = new StudentResponse();
                studentResponse.setStudentId(student.getId());
                studentResponse.setFirstName(student.getFirstName());
                studentResponse.setLastName(student.getLastName());
                studentResponse.setDob(student.getDob());
                studentResponse.setGender(student.getGender());
                studentResponseList.add(studentResponse);
            }
            return studentResponseList;
        }
    }

    public StudentResponse getStudentById(Integer studentId) {
        Optional<Student> student=studentRepository.findById(studentId);
        StudentResponse studentResponse=new StudentResponse();
        if(student.isPresent()){
            studentResponse.setStudentId(student.get().getId());
            studentResponse.setFirstName(student.get().getFirstName());
            studentResponse.setLastName(student.get().getLastName());
            studentResponse.setDob(student.get().getDob());
            studentResponse.setGender(student.get().getGender());
            return studentResponse;
        }else{
            throw new DataNotFoundException("Data not found with student id "+studentId);
        }
    }

    public ResponseEntity<Student> save(StudentRequest student) {
        Student student1= new Student();
        student1.setFirstName(student.getFirstName());
        student1.setLastName(student.getLastName());
        student1.setDob(student.getDob());
        student1.setGender(student.getGender());
        return new ResponseEntity<>(studentRepository.save(student1), HttpStatus.CREATED);
    }

    public void deleteData(Integer studentId) {
        List<StudentCourse> courseList=studentCourseRepository.findBystudentId(studentId);
        if(courseList.isEmpty()){
            throw new DataNotFoundException("Data is not found");
        }else{
        studentCourseRepository.deleteAll(courseList);
        Optional<Student> student = studentRepository.findById(studentId);
        studentRepository.delete(student.get());
        }
    }
}
