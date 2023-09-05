package com.example.proj.daoImpl;

import com.example.proj.dao.attrInfoDao;
import com.example.proj.entity.AttributesInformation;
import com.example.proj.repository.attrInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class attrInfoDaoImpl implements attrInfoDao {

    @Autowired
    private attrInfoRepository attrInforepository;

    @Override
    public AttributesInformation findAttrInfo(String attrName){
        return attrInforepository.findByAttrName(attrName);
    }

    @Override
    public void saveAttrInfo(AttributesInformation attrInfo){
        attrInforepository.save(attrInfo);
    }
}
