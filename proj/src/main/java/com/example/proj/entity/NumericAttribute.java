package com.example.proj.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumericAttribute extends Attribute{
    private double numVal;
    private String unit;

    public NumericAttribute(String name, String type, String match, double numVal, String unit){
        super(name, type, match);
        this.numVal = numVal;
        this.unit = unit;
    }
}
