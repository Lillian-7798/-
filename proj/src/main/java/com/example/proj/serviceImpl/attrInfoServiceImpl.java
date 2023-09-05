package com.example.proj.serviceImpl;

import com.example.proj.dao.attrInfoDao;
import com.example.proj.entity.AttributesInformation;
import com.example.proj.service.attrInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class attrInfoServiceImpl implements attrInfoService {

    @Autowired
    private attrInfoDao attrInfodao;

    @Override
    public AttributesInformation findAttrInfo(String attrName){
        return attrInfodao.findAttrInfo(attrName);
    }

    @Override
    public void saveAttrInfo(AttributesInformation attrInfo){
        attrInfodao.saveAttrInfo(attrInfo);
    }
}
