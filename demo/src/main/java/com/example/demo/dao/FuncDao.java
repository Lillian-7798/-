package com.example.demo.dao;

import com.example.demo.repository.FuncRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FuncDao {
    @Autowired
    private FuncRepository funcRepository;

    public List<Integer> getFunc(String column){
        return funcRepository.getFunc(column);
    }

    public List<Object[]> getAllFunc(){
        return funcRepository.getAllFunc();
    }

}
