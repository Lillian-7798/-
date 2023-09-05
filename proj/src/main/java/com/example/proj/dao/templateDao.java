package com.example.proj.dao;

import com.example.proj.entity.Attribute;
import com.example.proj.entity.BasicAttribute;
import com.example.proj.entity.Template;

import java.util.List;
import java.util.Map;

public interface templateDao {
    Attribute findMatchOpByAttrName(String attrName);
    List<Template> findSuitTemplate(Map<String, BasicAttribute> matchAttrMap);
    List<Template> findTemplateByAttrTypeInt(String attrName, String matchOp, Integer val);
    List<Template> findAll();
}
