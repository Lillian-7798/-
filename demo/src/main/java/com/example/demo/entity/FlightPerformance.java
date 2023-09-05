package com.example.demo.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FlightPerformance {
    private Double flightAltitude;
    private Double flightSpeed;
    private Double flightRange;
    private Double flightEndurance;

    public FlightPerformance(FlightPerformance f){
        this.flightAltitude = f.flightAltitude;
        this.flightSpeed = f.flightSpeed;
        this.flightRange = f.flightRange;
        this.flightEndurance = f.flightEndurance;
    }
    public FlightPerformance(){}















}
