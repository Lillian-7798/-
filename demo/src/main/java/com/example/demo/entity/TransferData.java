package com.example.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class TransferData {
    private Plan plan;
    private List<String> update;
}
