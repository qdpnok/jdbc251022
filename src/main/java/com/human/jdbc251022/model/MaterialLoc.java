package com.human.jdbc251022.model;

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class MaterialLoc {
    int invtId;
    String name;
    int qty;
    String loc;
}
