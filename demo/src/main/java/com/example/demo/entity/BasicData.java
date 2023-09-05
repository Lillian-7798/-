package com.example.demo.entity;

import lombok.Data;

@Data
public class BasicData {
    private Double totalWeight;
    private Double totalCost;

    public BasicData(BasicData b){
        this.totalWeight = b.totalWeight;
        this.totalCost = b.totalCost;
    }
    public BasicData(){}

}
