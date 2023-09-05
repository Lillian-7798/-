package com.example.demo.entity;

import lombok.Data;

@Data
public class PropulsionSystem {
    private Double propulsionAbility;
    private Double propulsionWeight;
    private Double propulsionCost;

    public PropulsionSystem(PropulsionSystem p){
        this.propulsionAbility = p.propulsionAbility;
        this.propulsionWeight = p.propulsionWeight;
        this.propulsionCost = p.propulsionCost;
    }
    public PropulsionSystem(){}
}
