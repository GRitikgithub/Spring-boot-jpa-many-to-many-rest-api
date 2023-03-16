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
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
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
            throw new DataNotFoundException("Data is not found");
        } else {
            if (!CollectionUtils.isEmpty(studentOptional.get().getCourses())) {
                for (Course course : studentOptional.get().getCourses()) {
                    CourseResponse courseResponse = new CourseResponse();
                    courseResponse.setCourseId(course.getId());
                    courseResponse.setName(course.getName());
                    courseResponseList.add(courseResponse);
                }
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
            throw new DataNotFoundException("Data is not found");
        }else{
            if(!CollectionUtils.isEmpty(courseOptional.get().getStudents())){
                for(Student student:courseOptional.get().getStudents()){
                    StudentResponse studentResponse=new StudentResponse();
                    studentResponse.setStudentId(student.getId());
                    studentResponse.setFirstName(student.getFirstName());
                    studentResponse.setLastName(student.getLastName());
                    studentResponse.setDob(student.getDob());
                    studentResponse.setGender(student.getGender());
                    studentResponseList.add(studentResponse);
                }
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
            throw new DataNotFoundException("Student is already enrolled");
        }else{
            StudentCourse studentCourse=new StudentCourse();
            return new ResponseEntity<>(studentCourseRepository.save(studentCourse), HttpStatus.CREATED);
        }
        }else{
            throw new DataNotFoundException("Student id or Course id doesn't exist");
        }
    }
    public ResponseEntity<StudentCourse> updateCourse(StudentCourse studentCourse){
        Optional<Student> studentOptional=studentRepository.findById(studentCourse.getStudentId());
        Optional<Course> courseOptional=courseRepository.findById(studentCourse.getCourseId());
        if (studentOptional.isPresent()&&courseOptional.isPresent()){
            Optional<StudentCourse> studentCourseOptional=studentCourseRepository.
                    findByStudentIdAndCourseId(studentCourse.getStudentId(),studentCourse.getCourseId());
            if(studentCourseOptional.isPresent()){
                throw new DataNotFoundException("Enroll course is not found");
            }else{
                StudentCourse studentCourse1 = studentCourseOptional.get();
                studentCourse1.setCourseId(studentCourse.getCourseId());
                return new ResponseEntity<>(studentCourseRepository.save(studentCourse1),HttpStatus.OK);
            }
        }else {
            throw new DataNotFoundException("Student id or Course id doesn't exist");
        }
    }

    public void deleteData(Integer studentId,Integer courseId){
        Optional<StudentCourse> studentCourseOptional=studentCourseRepository.
                findByStudentIdAndCourseId(studentId,courseId);
        if(studentCourseOptional.isPresent()){
            studentCourseRepository.delete(studentCourseOptional.get());
        }else{
            throw new DataNotFoundException("Student is not enroll with that course");
        }
    }
}
