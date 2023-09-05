package com.example.demo;


import com.example.demo.entity.Plan;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Rule {
    public static Map<String, List<String>> para_of_rule = new HashMap<>();

    public interface RuleInterface {
        boolean verify(Plan plan);
    }

    @Service("rule_1")
    public static class RuleImpl_1 implements RuleInterface {
        private String ruleName = "rule_1";
        {
            para_of_rule.put(ruleName, new ArrayList<>(Arrays.asList("length")));
        }

        @Override
        public boolean verify(Plan plan) {
            Double length = plan.getAerodynamicConfiguration().getLength();
            if (length > 5.0){
                return false;
            }
            return true;
        }
    }

    @Service("rule_2")
    public static class RuleImpl_2 implements RuleInterface {
        private String ruleName = "rule_2";
        {
            para_of_rule.put(ruleName, new ArrayList<>(Arrays.asList("AR")));
        }

        @Override
        public boolean verify(Plan plan) {
            Double AR = plan.getAerodynamicConfiguration().getAR();
            if (AR < 10 || AR > 15) {
                return false;
            }
            return true;
        }
    }

//    @Service("rule_3")
//    public static class RuleImpl implements RuleInterface {
//        private String ruleName = "rule_3";
//        {
////            System.load("C:\\Users\\Administrator.DESKTOP-72VUBCO\\Desktop\\Rule_test_plan\\x64\\Debug\\Rule_test_plan.dll");
//            para_of_rule.put(ruleName, new ArrayList<>(Arrays.asList("wing")));
//        }
//        @Override
//        public native boolean verify(Plan plan);
//    }


}






//public class Rule {
//    public static ArrayList<List<String>> para_of_rule = new ArrayList<>();
//
//    public interface RuleInterface {
//        boolean verify(Plan plan);
//    }
//
//    @Service("rule_test")
//    public static class RuleImpl implements RuleInterface {
//        {
//            System.load("C:\\Users\\Administrator.DESKTOP-72VUBCO\\Desktop\\Rule_test_plan\\x64\\Debug\\Rule_test_plan.dll");
//        }
//        @Override
//        public native boolean verify(Plan plan);
//    }
//
//}


