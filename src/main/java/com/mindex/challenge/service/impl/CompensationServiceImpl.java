package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service/////CompensationService
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;
    @Override
    public Compensation createCompensation(Compensation compensation) {
        LOG.debug("Creating employee [{}]", compensation);

        return compensationRepository.insert(compensation);
    }

    @Override
    public List<Compensation> readCompensation(String employeeId) {
        LOG.debug("Reading compensation records for employee [{}]", employeeId);

        return compensationRepository.findByEmployeeId(employeeId);
    }
}
