package com.exercise.FinalProject.response;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;
@Data
public class StudentResponse {


    private Integer studentId;

    private String firstName;

    private String lastName;

    private Date dob;

    private Character gender;
}
