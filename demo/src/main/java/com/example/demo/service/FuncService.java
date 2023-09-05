package com.example.demo.service;

import com.example.demo.dao.FuncDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncService {
    @Autowired
    private FuncDao funcDao;

    public List<Integer> getFunc(String column){
        return funcDao.getFunc(column);
    }

    public List<Object[]> getAllFunc(){
        return funcDao.getAllFunc();
    }

}
