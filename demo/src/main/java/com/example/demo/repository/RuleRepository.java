package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RuleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Integer> getRule(String column) {
        String sql = "select " + column + " from rule_index";
        System.out.println(column);
        Query query = entityManager.createNativeQuery(sql);
        List<Integer> result = query.getResultList();
        return result;
    }


}
