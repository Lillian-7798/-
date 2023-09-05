package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "rule_index")
public class RuleEntity {
    @Id
    @Column(name = "id")
    private int ruleId;

    private Integer length;
    private Integer weight;
    private Integer wing;
    private Integer AR;
}
