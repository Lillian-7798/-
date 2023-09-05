package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "function_para")
public class FuncEntity {
    @Id
    @Column(name = "id")
    private int funcId;

    private String para_1;
    private String para_2;
    private String para_3;
    private String para_4;
    private String para_5;
    private String para_6;

    public String getResultPara(){
        return para_1;
    }
    public List<String> getParaList(){
        List<String> list = new ArrayList<>();
        list.add(para_2);
        list.add(para_3);
        list.add(para_4);
        list.add(para_5);
        list.add(para_6);

        return list;
    }


}
