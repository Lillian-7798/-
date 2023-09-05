package com.example.demo.repository;


import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FuncRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Integer> getFunc(String column) {
        String sql = "select " + column + " from function_index";
//        System.out.println(column);
        Query query = entityManager.createNativeQuery(sql);

        List<Integer> result = query.getResultList();
        return result;
    }

    public List<Object[]> getAllFunc(){
        String sql = "select * from function_index";
        Query query = entityManager.createNativeQuery(sql);
        List<Object> result = query.getResultList();
        List<Object[]> res = new ArrayList<>();
        for (Object obj : result){
            Object[] row = (Object[])obj;
//            System.out.println(row[0]);
            res.add(row);
        }
        return res;
    }


}







