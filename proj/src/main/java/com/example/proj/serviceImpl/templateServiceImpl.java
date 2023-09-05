package com.example.proj.serviceImpl;

import com.example.proj.dao.templateDao;
import com.example.proj.entity.*;
import com.example.proj.service.attrInfoService;
import com.example.proj.service.templateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class templateServiceImpl implements templateService {
    @Autowired
    private templateDao TemplateDao;

    @Autowired
    private attrInfoService AttrInfoService;

    public double getDoubleVal(Template template, String attrName, String attrDetailName){
        List<Attribute> attrs = template.getAttributes().get(attrName).getAttrs();
        for(Attribute attr : attrs){
            if(Objects.equals(attr.getName(), attrDetailName)){
                return ((NumericAttribute)attr).getNumVal();
            }
        }
        return -1;
    }

    public String getStrVal(Template template, String attrName, String attrDetailName){
        List<Attribute> attrs = template.getAttributes().get(attrName).getAttrs();
        for(Attribute attr : attrs){
            if(Objects.equals(attr.getName(), attrDetailName)){
                return ((CharacterAttribute)attr).getStrVal();
            }
        }
        return "";
    }

    @Override
    public double getSemanticDistance(String arg1, String arg2) throws IOException, InterruptedException {
        String pythonScriptPath = "D:/pythonProject1/main.py";
        String[] pythonCommand = {"D:/pythonProject1/venv/Scripts/python.exe", pythonScriptPath, arg1, arg2};
        File workingDirectory = new File("D:/pythonProject1/venv/Scripts/");
        ProcessBuilder processBuilder = new ProcessBuilder(pythonCommand);
        processBuilder.directory(workingDirectory);
        Process process = processBuilder.start();
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        File workingDir = processBuilder.directory();
        //System.out.println("Working Directory: " + workingDir.getAbsolutePath());

        // 读取错误流并输出到控制台
        String errorLine;
        while ((errorLine = errorReader.readLine()) != null) {
            System.err.println("Python Error: " + errorLine);
        }
        int exitCode = process.waitFor(); // 等待进程执行完毕。
        //System.out.println(exitCode);
        if (exitCode == 0) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            double result = Double.parseDouble(line);
            return result;
        } else {
            throw new RuntimeException("调用Python函数失败。");
        }
    }

    @Override
    public List<Template> findSuitTemplate(Map<String, BasicAttribute> matchAttrMap) throws IOException, InterruptedException {
        for (String attrName : matchAttrMap.keySet()) {
            //System.out.print(TemplateDao.findMatchOpByAttrName(attrName));
            BasicAttribute basicAttribute = matchAttrMap.get(attrName);
//            System.out.println(attrName);
//            System.out.println(basicAttribute);
            AttributesInformation attrInfo = AttrInfoService.findAttrInfo(attrName);
            Map<String, AttrDetail> attrDetailMap = attrInfo.getAttrDetails();
            System.out.println(attrDetailMap);
            for(Attribute attr : basicAttribute.getAttrs()){
//                System.out.println(attr);
//                System.out.println(attr.getName());
//                System.out.println(attrDetailMap.get(attr.getName()));
                attr.setMatch((attrDetailMap.get(attr.getName())).getMatchOp());
            }
        }
        List<Template> results = TemplateDao.findSuitTemplate(matchAttrMap);
//        for(Template template : results){
//
//        }
        if(results.size() == 1){
            return results;
        }
        else{
            if(results.size() == 0){
                results = TemplateDao.findAll();
            }
            double curPoint = 1000;
            Template curTemplate = new Template();
            for(Template template : results){
                double point = 0;
                double total = 0;
                for(String attrName : matchAttrMap.keySet()){
                    BasicAttribute basicAttribute = matchAttrMap.get(attrName);
                    for(Attribute attr : basicAttribute.getAttrs()){
                        total += 1;
                        if(Objects.equals(attr.getType(), "double")){
                            NumericAttribute numAttr = (NumericAttribute)attr;
                            point +=  Math.abs(getDoubleVal(template, attrName, attr.getName()) - numAttr.getNumVal())/numAttr.getNumVal();
                        }
                        else if(Objects.equals(attr.getType(), "string") || Objects.equals(attr.getType(), "SD")){
                            String templateStrVal = getStrVal(template, attrName, attr.getName());
                            CharacterAttribute characterAttribute = (CharacterAttribute)attr;
                            double add = (1 - getSemanticDistance(templateStrVal, characterAttribute.getStrVal()));
                            point += add;
                            System.out.println(add);
                        }
                    }
                }
                point = 1/total * point;
                if(point < curPoint){
                    System.out.println("cur point is:"+point);
                    curPoint = point;
                    curTemplate = template;
                }
            }
            List<Template> finalResult = new ArrayList<>();
            finalResult.add(curTemplate);
            return finalResult;
        }
    }
}
