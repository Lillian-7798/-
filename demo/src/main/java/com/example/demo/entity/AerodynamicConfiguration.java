package com.example.demo.entity;

import lombok.Data;

@Data
public class AerodynamicConfiguration {
    private Double length;
    private Double wing;
    private Double AR;

    public AerodynamicConfiguration(AerodynamicConfiguration a){
        this.length = a.length;
        this.wing = a.wing;
        this.AR = a.AR;
    }
    public AerodynamicConfiguration(){}
}
