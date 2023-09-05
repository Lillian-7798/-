package com.example.demo.service;

import com.example.demo.dao.RuleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleService {
    @Autowired
    private RuleDao ruleDao;

    public List<Integer> getRule(String column){
        return ruleDao.getRule(column);
    }
}

