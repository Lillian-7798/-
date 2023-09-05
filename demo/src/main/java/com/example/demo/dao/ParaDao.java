package com.example.demo.dao;

import com.example.demo.entity.FuncEntity;
import com.example.demo.repository.ParaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ParaDao {
    @Autowired
    private ParaRepository paraRepository;

    public FuncEntity getFunc(Integer func_id){
        return paraRepository.getPara(func_id);
    }
}
