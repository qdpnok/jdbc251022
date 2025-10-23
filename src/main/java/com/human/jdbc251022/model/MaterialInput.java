package com.human.jdbc251022.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class MaterialInput {
    private int id;
    private int batchId;
    private int materialId;
    private int workOrderId;
    private int inputQty;
    private LocalDateTime date;

    public void setProcessNo(int processNo) {
    }

    public void setMaterialName(String materialName) {
    }

    public void setInputQuantity(int inputQuantity) {
    }

    public void setInputDate(LocalDateTime inputDate) {
    }

    public void setFactoryNo(int factoryNo) {
    }
}

