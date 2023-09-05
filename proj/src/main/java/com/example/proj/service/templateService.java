package com.example.proj.service;

import com.example.proj.entity.BasicAttribute;
import com.example.proj.entity.Template;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface templateService {
    double getSemanticDistance(String arg1, String arg2) throws IOException, InterruptedException;
    List<Template> findSuitTemplate(Map<String, BasicAttribute> matchAttrMap) throws IOException, InterruptedException;
}
