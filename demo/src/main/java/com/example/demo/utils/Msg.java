package com.example.demo.utils;

import com.example.demo.entity.Plan;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Msg {
    // 0 正常
    // -1 rule未通过
    // -2 覆盖用户参数
    // -3 参数有环
    private Integer msg = 0;
    private List<String> errorPara = new ArrayList<>();
    private Plan plan = new Plan();
    private List<String> change = new ArrayList<>();

    public Msg() {}

    public Msg(Integer m, Plan p){
        msg = m;
        plan = p;
    }

    public Msg(Integer m, List<String> change,Plan p){
        msg = m;
        plan = p;
        this.change.addAll(change);
    }

    public Msg(Integer msg, Plan p, List<String> errorPara){
        this.msg = msg;
        plan = p;
        this.errorPara.addAll(errorPara);
    }
}
