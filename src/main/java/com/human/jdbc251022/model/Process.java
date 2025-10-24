package com.human.jdbc251022.model;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Process {
    private int PROCESS_ID;
    private String PROCESS_NAME;
    private int PRODUCT_ID;
}
