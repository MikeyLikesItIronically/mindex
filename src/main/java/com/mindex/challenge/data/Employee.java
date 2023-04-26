package com.mindex.challenge.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String position;
    private String department;
    private List<Employee> directReports;

}
