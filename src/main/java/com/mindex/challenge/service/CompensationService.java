package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

import java.util.List;

public interface CompensationService {
    Compensation createCompensation(Compensation compensation);
    List<Compensation> readCompensation(String employeeId);
}
