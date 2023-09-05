package com.example.proj.service;

import com.example.proj.entity.AttributesInformation;

public interface attrInfoService {
    AttributesInformation findAttrInfo(String attrName);
    void saveAttrInfo(AttributesInformation attrInfo);
}
