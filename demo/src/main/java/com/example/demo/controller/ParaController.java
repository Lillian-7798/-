package com.example.demo.controller;

import com.example.demo.entity.Graph;
import com.example.demo.entity.Node;
import com.example.demo.entity.TaskSystem;
import com.example.demo.entity.TransferData;
import com.example.demo.service.FuncService;
import com.example.demo.service.ParaService;
import com.example.demo.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ParaController {
    @Autowired
    private ParaService paraService;

    @PostConstruct
    public void graphInitialize(){
        paraService.graphInitialize();
    }


    @QueryMapping
    public TaskSystem hello(){
        TaskSystem t = new TaskSystem();
        t.setTaskCost(111.0);
        t.setTaskWeight(222.0);
        return t;
    }

    @QueryMapping
    public TaskSystem testHello(@Argument TransferData data){
        System.out.println(data);
        TaskSystem t = new TaskSystem();
        t.setTaskCost(100.0);
        t.setTaskWeight(222.0);
        return t;
    }

    @QueryMapping
    public Msg calculatePara(@Argument TransferData data) {
        return paraService.CalculatePara(data);
    }
}
