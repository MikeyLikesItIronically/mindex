package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    @Autowired
    private CompensationService compensationService;

    @Autowired
    private EmployeeService employeeService;

    @Before
    public void setup() {
    }

    @Test
    public void testCreateRead() {
        // create an employee for this test
        Employee testEmployee = employeeService.create(Employee.builder().firstName("Compensation")
                .lastName("Test").build());

        // instantiate the compensation object
        Compensation initialCompensation = Compensation.builder().employeeId(testEmployee.getEmployeeId())
                .salary(140000).effectiveDate(LocalDateTime.parse("2022-12-03T10:15:30")).build();

        // create the compensation
        Compensation createdCompensation = compensationService.createCompensation(initialCompensation);

        // check the create against the initial
        assertEquals(createdCompensation.getSalary(), initialCompensation.getSalary());
        assertEquals(createdCompensation.getEffectiveDate(), initialCompensation.getEffectiveDate());
        assertEquals(createdCompensation.getEmployeeId(), initialCompensation.getEmployeeId());

        // read the compensation
        List<Compensation> compensationList = compensationService.readCompensation(testEmployee.getEmployeeId());

        // verify the read compensation
        assertNotNull(compensationList);
        assertEquals(compensationList.size(), 1);

        Compensation readCompensation = compensationList.get(0);
        assertEquals(readCompensation.getSalary(), initialCompensation.getSalary());
        assertEquals(readCompensation.getEffectiveDate(), initialCompensation.getEffectiveDate());
        assertEquals(readCompensation.getEmployeeId(), initialCompensation.getEmployeeId());
    }
}
