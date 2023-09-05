package com.example.demo;

import com.example.demo.entity.Plan;
import org.springframework.stereotype.Service;

@Service
public class Function {
    public interface FunctionInterface {
//        void func(Map<String, Double> paras);
        void func(Plan plan);
    }

    // 总重量计算
    @Service("function_1")
    public class functionImpl1 implements  FunctionInterface {
        @Override
        public void func(Plan plan){
            Double bodyWeight = plan.getBodyStructure().getBodyWeight();
            Double energyWeight = plan.getEnergySystem().getEnergyWeight();
            Double propulsionWeight = plan.getPropulsionSystem().getPropulsionWeight();
            Double taskWeight = plan.getTaskSystem().getTaskWeight();
            plan.getBasicData().setTotalWeight(bodyWeight + energyWeight + propulsionWeight + taskWeight);

        }
    }

    // 总成本计算
    @Service("function_2")
    public class functionImpl2 implements  FunctionInterface {
        @Override
        public void func(Plan plan){
            Double bodyCost = plan.getBodyStructure().getBodyCost();
            Double energyCost = plan.getEnergySystem().getEnergyCost();
            Double propulsionCost = plan.getPropulsionSystem().getPropulsionCost();
            Double taskCost = plan.getTaskSystem().getTaskCost();
            Double groundCost = plan.getGroundSupport().getGroundCost();
            plan.getBasicData().setTotalCost(bodyCost + energyCost + propulsionCost + taskCost + groundCost);
        }
    }


    // 飞行速度计算
    @Service("function_3")
    public class functionImpl3 implements  FunctionInterface {
        @Override
        public void func(Plan plan){
            Double length = plan.getAerodynamicConfiguration().getLength();
            Double wing = plan.getAerodynamicConfiguration().getWing();
            Double AR = plan.getAerodynamicConfiguration().getAR();
            Double totalWeight = plan.getBasicData().getTotalWeight();
            Double propulsionAbility = plan.getPropulsionSystem().getPropulsionAbility();
            Double speed = length + wing + AR + propulsionAbility - totalWeight;
            plan.getFlightPerformance().setFlightSpeed(speed);
        }
    }

    // 环测试函数
    @Service("function_4")
    public class functionImpl4 implements FunctionInterface {
        @Override
        public void func(Plan plan){
            Double flightSpeed = plan.getFlightPerformance().getFlightSpeed();
            plan.getBodyStructure().setBodyWeight(flightSpeed);
        }
    }













    //    @Service("function_1")
//    public class functionImpl1 implements  FunctionInterface {
//        @Override
//        public void func(Map<String, Double> paras){
//            Double length = paras.get("length");
//            Double wing = paras.get("wing");
//            paras.put("weight", length + wing);
//        }
//    }
//
//    @Service("function_2")
//    public class functionImpl2 implements  FunctionInterface {
//        @Override
//        public void func(Map<String, Double> paras){
//            Double length = paras.get("length");
//            Double wing = paras.get("wing");
//            paras.put("weight", length + wing);
//        }
//    }
//
//    @Service("function_3")
//    public class functionImpl3 implements  FunctionInterface {
//        @Override
//        public void func(Map<String, Double> paras){
//            Double length = paras.get("length");
//            Double wing = paras.get("wing");
//            paras.put("weight", length + wing);
//        }
//    }
}
