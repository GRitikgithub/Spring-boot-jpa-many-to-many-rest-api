package com.exercise.FinalProject.controller;

import com.exercise.FinalProject.service.StudentService;
import com.exercise.FinalProject.repository.StudentRepository;
import com.exercise.FinalProject.request.StudentRequest;
import com.exercise.FinalProject.response.StudentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class StudentController {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentService studentService;

    @GetMapping("/students")
    public List<StudentResponse> getData() {
        return studentService.getAllData();
    }

   @GetMapping("/students/{studentId}")
    public StudentResponse getStudent(@PathVariable Integer studentId) {
        return studentService.getStudentById(studentId);
    }

    @PostMapping("/students")
    public String addData(@RequestBody StudentRequest student) {
        this.studentService.save(student);
        log.info("New student data insert");
        return "New student data insert successfully....";
    }
    @DeleteMapping("/students/{studentId}")
    public void delete(@PathVariable Integer studentId) {
        this.studentService.deleteData(studentId);
    }
}
