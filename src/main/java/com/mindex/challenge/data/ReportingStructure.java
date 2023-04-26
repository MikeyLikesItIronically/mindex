package com.mindex.challenge.data;

import lombok.Data;
import lombok.NonNull;

@Data
public class ReportingStructure {
    @NonNull
    private Employee employee;
    /**
     * This should be the total number of reports, down to the leaf level of the hierarchy.
     */
    @NonNull
    private Integer numberOfReports;
}
