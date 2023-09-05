package com.example.demo.entity;

import lombok.Data;

@Data
public class TaskSystem {
    private Double taskWeight;
    private Double taskCost;

    public TaskSystem(TaskSystem t){
        this.taskWeight = t.taskWeight;
        this.taskCost = t.taskCost;
    }
    public TaskSystem(){}
}
