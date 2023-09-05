package com.example.demo.entity;

import lombok.Data;

@Data
public class BodyStructure {
    private enum MaterialType {material_1, material_2;}
    private MaterialType materialType;
    private Double bodyWeight;
    private Double bodyCost;

    public BodyStructure(BodyStructure b){
        this.materialType = b.materialType;
        this.bodyWeight = b.bodyWeight;
        this.bodyCost = b.bodyCost;
    }
    public BodyStructure(){}
}


