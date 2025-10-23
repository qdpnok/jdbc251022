package com.human.jdbc251022.model;

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class MaterialLoc {
    private int id;
    private String name;
    private int qty;
    private String loc;
}
