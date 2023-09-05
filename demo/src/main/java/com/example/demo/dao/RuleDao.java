package com.example.demo.dao;

import com.example.demo.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RuleDao {
    @Autowired
    private RuleRepository ruleRepository;

    public List<Integer> getRule(String column){
        return ruleRepository.getRule(column);
    }
}
