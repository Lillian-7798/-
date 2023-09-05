package com.example.demo.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import net.sf.json.JSONObject;

@Data
public class Plan {
    // 飞行能力
    private FlightPerformance flightPerformance;
    // 重量成本与寿命
    private BasicData basicData;
    // 气动布局
    private AerodynamicConfiguration aerodynamicConfiguration;
    // 机体结构
    private BodyStructure bodyStructure;
    // 能源系统
    private EnergySystem energySystem;
    // 推进系统
    private PropulsionSystem propulsionSystem;
    // 任务系统
    private TaskSystem taskSystem;
    // 地面保障
    private GroundSupport groundSupport;

    public Plan(Plan p){
        this.flightPerformance = new FlightPerformance(p.flightPerformance);
        this.basicData = new BasicData(p.basicData);
        this.aerodynamicConfiguration = new AerodynamicConfiguration(p.aerodynamicConfiguration);
        this.bodyStructure = new BodyStructure(p.bodyStructure);
        this.energySystem = new EnergySystem(p.energySystem);
        this.propulsionSystem = new PropulsionSystem(p.propulsionSystem);
        this.taskSystem = new TaskSystem(p.taskSystem);
        this.groundSupport = new GroundSupport(p.groundSupport);
    }

    public Plan(){}

    public JSONObject toJsonObj(){
        JSONObject jsFP = JSONObject.fromObject(flightPerformance);
        JSONObject jsBD = JSONObject.fromObject(basicData);
        JSONObject jsAC = JSONObject.fromObject(aerodynamicConfiguration);
        JSONObject jsBS = JSONObject.fromObject(bodyStructure);
        JSONObject jsES = JSONObject.fromObject(energySystem);
        JSONObject jsPS = JSONObject.fromObject(propulsionSystem);
        JSONObject jsTS = JSONObject.fromObject(taskSystem);
        JSONObject jsGS = JSONObject.fromObject(groundSupport);
        JSONObject jsPlan = new JSONObject();
        jsPlan.putAll(jsFP);
        jsPlan.putAll(jsBD);
        jsPlan.putAll(jsAC);
        jsPlan.putAll(jsBS);
        jsPlan.putAll(jsES);
        jsPlan.putAll(jsPS);
        jsPlan.putAll(jsTS);
        jsPlan.putAll(jsGS);
        return jsPlan;
    }

}
