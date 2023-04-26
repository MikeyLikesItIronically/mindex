package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    // We need mongoTemplate to do the hierarchy queries
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    @Override
    public int getNumReports(String employeeId) {

        // TODO: This is not supported in the version of Mongo we're working with
//        TypedAggregation<Employee> agg = Aggregation.newAggregation(Employee.class,
//                match(Criteria.where("employeeId").is(employeeId)), //
//                Aggregation.graphLookup("employee") //
//                        .startWith("directReports") //
//                        .connectFrom("directReports") //
//                        .connectTo("employeeId") //
//                        .depthField("depth") //
//                        .maxDepth(5) //
//                        .as("reportingStructure"));
//
//        AggregationResults<Document> aggResults = mongoTemplate.aggregate(agg, Document.class);
//
//        Document object = aggResults.getUniqueMappedResult();
//        List<Object> list = (List<Object>) object.get("reportingStructure");
//
//        for (Object result: list) {
//            System.out.println("Result: "+result);
//        }
//        // This probably isn't right, but we need this to run in order to verify its working
//        return list.size();

        Employee supervisingEmployee = read(employeeId);

        return getNumReports(supervisingEmployee, 5);
    }

    /**
     *
     * @param supervisingEmployee The employee we want the number of reports for.
     * @param depth we need a depth so we don't recurse ourselves out of memory
     * @return
     */
    private int getNumReports(Employee supervisingEmployee, int depth) {
        int numReports = 0;
        List<Employee> directReports = supervisingEmployee.getDirectReports();
        if (depth<1) {
            //There wasn't any exception behavior specified, so I'm just doing a runtime exception here to make it
            // easier to code.  Ordinarily there would be an exception to cover this scenario that would be handled in a
            // specific way.
            throw new RuntimeException("Exceeded specified depth");
        }
        else if (directReports!=null && directReports.size()>0){
            numReports += directReports.size();

            for (Employee reportingEmployee : directReports) {
                numReports += getNumReports(reportingEmployee, depth-1);
            }
        }
        return numReports;
    }


}
