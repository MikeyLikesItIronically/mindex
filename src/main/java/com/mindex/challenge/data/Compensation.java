package com.mindex.challenge.data;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Compensation {
    String employeeId;
    int salary;
    LocalDateTime effectiveDate;
}
