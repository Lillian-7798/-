package com.example.demo.entity;

import lombok.Data;

@Data
public class GroundSupport {
    private Double groundCost;

    public GroundSupport(GroundSupport g){
        this.groundCost = g.groundCost;
    }
    public GroundSupport(){}
}
