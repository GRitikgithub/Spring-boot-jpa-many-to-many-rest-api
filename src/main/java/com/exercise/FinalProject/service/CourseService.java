package com.exercise.FinalProject.service;

import com.exercise.FinalProject.exception.DataNotFoundException;
import com.exercise.FinalProject.model.Course;
import com.exercise.FinalProject.model.StudentCourse;
import com.exercise.FinalProject.repository.CourseRepository;
import com.exercise.FinalProject.repository.StudentCourseRepository;
import com.exercise.FinalProject.request.CourseRequest;
import com.exercise.FinalProject.response.CourseResponse;
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
public class CourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    StudentCourseRepository studentCourseRepository;

    public List<CourseResponse> getAllData() {
        List<CourseResponse> courseResponseList = new ArrayList<>();
        List<Course> courseList = courseRepository.findAll();
        if(CollectionUtils.isEmpty(courseList)){
            return null;
        }else{
            for(Course course: courseList){
                CourseResponse courseResponse = new CourseResponse();
                courseResponse.setCourseId(course.getId());
                courseResponse.setName(course.getName());
                courseResponseList.add(courseResponse);
            }
            return courseResponseList;
        }
    }

    public ResponseEntity<Course> insert(CourseRequest course) {
        Course course1= new Course();
        course1.setName(course.getName());
        return new ResponseEntity<>(courseRepository.save(course1), HttpStatus.CREATED);
    }

    public void deleteData(Integer courseId) {
        List<StudentCourse> courseList=studentCourseRepository.findByCourseId(courseId);
        if(courseList.isEmpty()){
            throw new DataNotFoundException("Data is not found");
        }else{
        studentCourseRepository.deleteAll(courseList);
        Optional<Course> course = courseRepository.findById(courseId);
        courseRepository.delete(course.get());
    }
    }
}
