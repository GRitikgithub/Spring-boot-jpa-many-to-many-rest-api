package com.exercise.FinalProject.request;

import com.exercise.FinalProject.response.StudentResponse;
import lombok.Data;

import java.util.Date;

@Data
public class StudentRequest {

    private Integer studentId;

    private String firstName;

    private String lastName;

    private Date dob;

    private Character gender;

}
