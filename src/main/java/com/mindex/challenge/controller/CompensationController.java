package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    //need a create and a read
    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received employee create request for [{}]", compensation);

        return compensationService.createCompensation(compensation);
    }

    @GetMapping("/compensation/{employeeId}")
    public List<Compensation> read(@PathVariable String employeeId) {
        LOG.debug("Received employee read request for id [{}]", employeeId);

        return compensationService.readCompensation(employeeId);
    }
}
