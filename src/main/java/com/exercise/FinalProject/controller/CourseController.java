package com.exercise.FinalProject.controller;

import com.exercise.FinalProject.repository.CourseRepository;
import com.exercise.FinalProject.request.CourseRequest;
import com.exercise.FinalProject.response.CourseResponse;
import com.exercise.FinalProject.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CourseController {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseService courseService;

    @GetMapping("/courses")
    public List<CourseResponse> getData() {
        return courseService.getAllData();
    }
    @PostMapping("/courses")
    public String addData(@RequestBody CourseRequest course) {
        this.courseService.insert(course);
        log.info("New course data insert");
        return "Data is insert successfully....";
    }
    @DeleteMapping("/courses/{courseId}")
    public void delete(@PathVariable Integer courseId){
        this.courseService.deleteData(courseId);
    }

}
