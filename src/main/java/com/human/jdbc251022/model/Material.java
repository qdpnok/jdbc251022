package com.human.jdbc251022.model;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Material {
    int id;
    String name;
    String type;

    @Override
    public String toString() {
        return "아이디: " + id + "\n" +
                "이름: " + name + "\n" +
                "종류: " + type + "\n" +
                "-".repeat(10);
    }

}