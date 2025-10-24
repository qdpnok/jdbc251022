package com.human.jdbc251022.model;

import lombok.*;

import java.util.Date;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor

public class Sample {

    private int sampleId;
    private int batchId;
    private int resultId;
    private String sampleDate;

    public Sample(int sampleId, int batchId, int resultId, Date sampleDate) {
    }
}