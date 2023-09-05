package com.example.demo.repository;

import com.example.demo.entity.FuncEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ParaRepository {
    @PersistenceContext
    private EntityManager entityManager;
    public FuncEntity getPara(Integer func_id){
        String sql = "select * from function_para where id=" + func_id;
        Query query = entityManager.createNativeQuery(sql, FuncEntity.class);
        List<FuncEntity> result = query.getResultList();
        return result.get(0);
    }
}
