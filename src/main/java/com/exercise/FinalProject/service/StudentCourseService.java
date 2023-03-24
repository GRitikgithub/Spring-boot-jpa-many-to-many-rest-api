package com.exercise.FinalProject.service;

import com.exercise.FinalProject.exception.DataNotFoundException;
import com.exercise.FinalProject.model.Course;
import com.exercise.FinalProject.model.Student;
import com.exercise.FinalProject.model.StudentCourse;
import com.exercise.FinalProject.repository.CourseRepository;
import com.exercise.FinalProject.repository.StudentCourseRepository;
import com.exercise.FinalProject.repository.StudentRepository;
import com.exercise.FinalProject.response.CourseResponse;
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
public class StudentCourseService {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentCourseRepository studentCourseRepository;

    public List<CourseResponse> getCourseData(Integer studentId) {
        List<CourseResponse> courseResponseList = new ArrayList<>();
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isEmpty()) {
            log.info("Data is not found");
            throw new DataNotFoundException("Data is not found");
        } else {
            if (!CollectionUtils.isEmpty(studentOptional.get().getCourses())) {
                for (Course course : studentOptional.get().getCourses()) {
                    CourseResponse courseResponse = new CourseResponse();
                    courseResponse.setCourseId(course.getCourseId());
                    courseResponse.setName(course.getName());
                    courseResponseList.add(courseResponse);
                }
                log.info("Fetch list of all courses the student is registered.");
                return courseResponseList;
            } else {
                return null;
            }
        }
    }
    public List<StudentResponse> getStudentData(Integer courseId){
        List<StudentResponse> studentResponseList=new ArrayList<>();
        Optional<Course> courseOptional=courseRepository.findById(courseId);
        if(courseOptional.isEmpty()){
            log.info("Data is not found");
            throw new DataNotFoundException("Data is not found");
        }else{
            if(!CollectionUtils.isEmpty(courseOptional.get().getStudents())){
                for(Student student:courseOptional.get().getStudents()){
                    StudentResponse studentResponse=new StudentResponse();
                    studentResponse.setStudentId(student.getStudentId());
                    studentResponse.setFirstName(student.getFirstName());
                    studentResponse.setLastName(student.getLastName());
                    studentResponse.setDob(student.getDob());
                    studentResponse.setGender(student.getGender());
                    studentResponseList.add(studentResponse);
                }
                log.info("Fetch list of all students registered in given course");
                return studentResponseList;
            }else{
                return null;
            }
        }
    }

    public ResponseEntity<StudentCourse> saved(Integer studentId, Integer courseId) {
        Optional<Student> studentOptional=studentRepository.findById(studentId);
        Optional<Course> courseOptional=courseRepository.findById(courseId);
        if(studentOptional.isPresent()&&courseOptional.isPresent()){
        Optional<StudentCourse> studentCourseOptional=studentCourseRepository.
                findByStudentIdAndCourseId(studentId,courseId);
        if(studentCourseOptional.isPresent()){
            log.info("Student is already enrolled");
            throw new DataNotFoundException("Student is already enrolled");
        }else{
            StudentCourse studentCourse=new StudentCourse();
            studentCourse.setStudentId(studentId);
            studentCourse.setCourseId(courseId);
            log.info("Enroll student in a course");
            return new ResponseEntity<>(studentCourseRepository.save(studentCourse), HttpStatus.CREATED);
        }
        }else{
            log.info("Student id or Course id doesn't exist");
            throw new DataNotFoundException("Student id or Course id doesn't exist");
        }
    }
    public ResponseEntity<StudentCourse> updateCourse(Integer studentId, Integer courseId,StudentCourse studentCourse){
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (studentOptional.isPresent() && courseOptional.isPresent()) {
            Optional<StudentCourse> studentCourseOptional = studentCourseRepository.
                    findByStudentIdAndCourseId(studentId, courseId);
            if (studentCourseOptional.isPresent()) {
                Optional<Course> newCourseOptional = courseRepository.findById(studentCourse.getCourseId());
                if (newCourseOptional.isPresent()) {
                    StudentCourse studentCourse1 = studentCourseOptional.get();
                    studentCourse1.setCourseId(studentCourse.getCourseId());
                    return new ResponseEntity<>(studentCourseRepository.save(studentCourse1), HttpStatus.CREATED);
                } else {
                    throw new DataNotFoundException("New Course id doesn't exist");
                }
            } else {
                throw new DataNotFoundException("course is not enroll with student");
            }
        } else {
            throw new DataNotFoundException("Student id or Course id doesn't exist");
        }
    }

    public void deleteData(Integer studentId,Integer courseId){
        Optional<StudentCourse> studentCourseOptional=studentCourseRepository.
                findByStudentIdAndCourseId(studentId,courseId);
        if(studentCourseOptional.isPresent()){
            studentCourseRepository.delete(studentCourseOptional.get());
            log.info("Delete the course of the student");
        }else{
            log.info("Student is not enroll with that course");
            throw new DataNotFoundException("Student is not enroll with that course");
        }
    }
}
