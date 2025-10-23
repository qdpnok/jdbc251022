package com.human.jdbc251022.model;


import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class QCResult {
    private int id;
    private int sampleId;
    private String isSuccess;
    private int batchId;
    private int resultId;
}
