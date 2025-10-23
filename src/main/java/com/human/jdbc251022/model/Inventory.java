package com.human.jdbc251022.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Inventory {
    private int id;
    private int matId;
    private int ibId;
    private LocalDateTime expDate;
    private int qty;
    private String loc;
}
