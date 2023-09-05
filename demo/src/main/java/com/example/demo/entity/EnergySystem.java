package com.example.demo.entity;

import lombok.Data;

@Data
public class EnergySystem {
    private Double energyEndurance;
    private Double energyAbility;
    private Double energyWeight;
    private Double energyCost;

    public EnergySystem(EnergySystem e){
        this.energyEndurance = e.energyEndurance;
        this.energyAbility = e.energyAbility;
        this.energyWeight = e.energyWeight;
        this.energyCost = e.energyCost;
    }
    public EnergySystem(){}

}
