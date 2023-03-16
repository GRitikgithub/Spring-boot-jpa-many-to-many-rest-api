package com.exercise.FinalProject.controller;

import com.exercise.FinalProject.model.Course;
import com.exercise.FinalProject.model.Student;
import com.exercise.FinalProject.model.StudentCourse;
import com.exercise.FinalProject.repository.CourseRepository;
import com.exercise.FinalProject.repository.StudentRepository;
import com.exercise.FinalProject.response.CourseResponse;
import com.exercise.FinalProject.response.StudentResponse;
import com.exercise.FinalProject.service.StudentCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Slf4j
public class StudentCourseController {

    @Autowired
    StudentCourseService studentCourseService;

    @GetMapping("/students/{studentId}/course")
    public List<CourseResponse> getCourse(@PathVariable Integer studentId) {
        return studentCourseService.getCourseData(studentId);
    }
    @GetMapping("/courses/{courseId}/student")
    public List<StudentResponse> getStudent(@PathVariable Integer courseId){
        return studentCourseService.getStudentData(courseId);
    }
    @PostMapping("/students/{studentId}/course/{courseId}")
    public String addData(@PathVariable Integer studentId,@PathVariable Integer courseId){
        this.studentCourseService.saved(studentId,courseId);
        return "Student enroll successfully";
    }
    @DeleteMapping("students/{studentId}/course/{courseId}")
    public void delete(@PathVariable Integer studentId,@PathVariable Integer courseId){
        studentCourseService.deleteData(studentId,courseId);
    }
   @PutMapping("students/{studentId}/course/{courseId}")
    public String update(@PathVariable Integer studentId, @PathVariable Integer courseId,
                         @RequestBody StudentCourse studentCourse){
        studentCourse.setStudentId(studentId);
        studentCourse.setCourseId(courseId);
        this.studentCourseService.updateCourse(studentCourse);
        return "Course update successfully";
    }

}
