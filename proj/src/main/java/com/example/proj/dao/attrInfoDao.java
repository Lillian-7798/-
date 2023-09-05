package com.example.proj.dao;

import com.example.proj.entity.AttributesInformation;

public interface attrInfoDao {
    AttributesInformation findAttrInfo(String attrName);
    void saveAttrInfo(AttributesInformation attrInfo);
}
