package com.example.proj.controller;

import com.example.proj.entity.*;
import com.example.proj.repository.templateRepository;
import com.example.proj.service.attrInfoService;
import com.example.proj.service.templateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
public class templateController {

    @Autowired
    private templateRepository repository;

    @Autowired
    private templateService TemplateService;

//    @Autowired
//    private MongoTemplate mongoTemplate;

    @Autowired
    private attrInfoService AttrInfoService;

    private Attribute fillAttr(Attribute attr, String name, String type, String match){
        attr.setName(name);
        attr.setType(type);
        attr.setMatch(match);
        return attr;
    }

    @RequestMapping("/addDocument")
    public List<Template> addAndGet(){
        //Map<String, Attribute> attributes = new HashMap<>();
        Map<String, BasicAttribute> template1 = new HashMap<>();
        List<Attribute> attributes = new ArrayList<>();
        Attribute attribute = new NumericAttribute("cost", "double", "le", 40000,"$");
        attributes.add(attribute);
        BasicAttribute basicAttribute = new BasicAttribute(attributes);
        template1.put("cost", basicAttribute);

        attributes = new ArrayList<>();
        attribute = new NumericAttribute("loading_capacity", "double", "ge", 200,"kg");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("loading_capacity", basicAttribute);

        attributes = new ArrayList<>();
        attribute = new NumericAttribute("endurance", "double", "ge", 80,"h");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("endurance", basicAttribute);

        //Map<String ,String> kvVal = new HashMap<>();
        attributes = new ArrayList<>();
        attribute = new CharacterAttribute("mission type", "string", "eq", "大气研究");
        attributes.add(attribute);
        attribute = new CharacterAttribute("mission height", "range", "in", "7000-8000m");
        attributes.add(attribute);
        attribute = new NumericAttribute("mission coverage area", "double", "ge", 10, "km");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("mission capacity", basicAttribute);


        attributes = new ArrayList<>();
        attribute = new CharacterAttribute("take off and landing requirements", "string", "eq", "无");
        attributes.add(attribute);
        attribute = new NumericAttribute("height", "double", "ge", 9, "km");
        attributes.add(attribute);
        attribute = new NumericAttribute("speed", "double", "ge", 50, "km/h");
        attributes.add(attribute);
        attribute = new NumericAttribute("flight range", "double", "ge", 100, "km");
        attributes.add(attribute);
        attribute = new NumericAttribute("endurance", "double", "ge", 80, "h");
        attributes.add(attribute);
        attribute = new NumericAttribute("flight service life", "double", "ge", 1000, "h");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("flight capacity", basicAttribute);


//        attributes = new ArrayList<>();
//        attribute = new CharacterAttribute("weight, cost and service time", "string","eq", attributes);
//        attributes.put("weight and service time", attribute);

        attributes = new ArrayList<>();
        attribute = new CharacterAttribute("single fuselage", "string","eq", "无");
        attributes.add(attribute);
        attribute = new NumericAttribute("length", "double", "ge", 25, "m");
        attributes.add(attribute);
        attribute = new NumericAttribute("wing span", "double", "ge", 10, "m");
        attributes.add(attribute);
        attribute = new NumericAttribute("aspect ratio", "double", "ge", 4, "");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("aerodynamic configuration", basicAttribute);

        attributes = new ArrayList<>();
        attribute = new CharacterAttribute("structural layout", "string","eq", "常规布局，长机翼");
        attributes.add(attribute);
        attribute = new CharacterAttribute("strength and stiffness", "string","eq", "航空强度");
        attributes.add(attribute);
        attribute = new CharacterAttribute("materials", "string","eq", "合金等");
        attributes.add(attribute);
        attribute = new CharacterAttribute("manufacturing techniques", "string","eq", "无");
        attributes.add(attribute);
        attribute = new NumericAttribute("weight", "double", "le", 50, "kg");
        attributes.add(attribute);
        attribute = new NumericAttribute("cost", "double", "le", 20000, "$");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("airframe structure", basicAttribute);

        attributes = new ArrayList<>();
        attribute = new CharacterAttribute("photovoltaic system", "string","eq", "太阳能光伏");
        attributes.add(attribute);
        attribute = new CharacterAttribute("energy storage battery", "string","eq", "大储量电池");
        attributes.add(attribute);
        attribute = new NumericAttribute("maximum output", "double", "ge", 20, "");
        attributes.add(attribute);
        attribute = new NumericAttribute("endurance", "double", "ge", 80, "h");
        attributes.add(attribute);
        attribute = new NumericAttribute("weight", "double", "le", 50, "kg");
        attributes.add(attribute);
        attribute = new NumericAttribute("cost", "double", "le", 5000, "$");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("energy system", basicAttribute);

        attributes = new ArrayList<>();
        attribute = new CharacterAttribute("electric motor", "string","eq", "高转速");
        attributes.add(attribute);
        attribute = new CharacterAttribute("propeller", "string","eq", "无");
        attributes.add(attribute);
        attribute = new NumericAttribute("power", "double", "ge", 10, "");
        attributes.add(attribute);
        attribute = new NumericAttribute("weight", "double", "le", 100, "kg");
        attributes.add(attribute);
        attribute = new NumericAttribute("cost", "double", "le", 10000, "$");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("propulsion system", basicAttribute);

        attributes = new ArrayList<>();
        attribute = new CharacterAttribute("communication", "string","eq", "无线电等");
        attributes.add(attribute);
        attribute = new CharacterAttribute("reconnaissance", "string","eq", "北斗卫星");
        attributes.add(attribute);
        attribute = new NumericAttribute("weight", "double", "le", 30, "kg");
        attributes.add(attribute);
        attribute = new NumericAttribute("cost", "double", "le", 6000, "$");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("mission system", basicAttribute);

        attributes = new ArrayList<>();
        attribute = new CharacterAttribute("flight control and actuation", "string","eq", "无");
        attributes.add(attribute);
        attribute = new CharacterAttribute("integrated management of energy, propulsion, and missions", "string","eq", "无");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("flight management system", basicAttribute);

        attributes = new ArrayList<>();
        attribute = new CharacterAttribute("ground station", "string","eq", "地面观测站");
        attributes.add(attribute);
        attribute = new CharacterAttribute("take off and landing", "string","eq", "起降机场");
        attributes.add(attribute);
        attribute = new CharacterAttribute("transportation and storage", "string","eq", "公路或铁路运输");
        attributes.add(attribute);
        attribute = new CharacterAttribute("inspection and maintenance", "string","eq", "检测维修专业技术人员");
        attributes.add(attribute);
        attribute = new NumericAttribute("cost", "double", "le", 4000, "$");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("ground support", basicAttribute);

        Template template = new Template(1, template1);
        repository.save(template);

        template1 = new HashMap<>();
        attributes = new ArrayList<>();
        attribute = new NumericAttribute("cost", "double", "le", 20000,"$");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("cost", basicAttribute);

        attributes = new ArrayList<>();
        attribute = new NumericAttribute("loading_capacity", "double", "ge", 100,"kg");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("loading_capacity", basicAttribute);

        attributes = new ArrayList<>();
        attribute = new NumericAttribute("endurance", "double", "ge", 60,"h");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("endurance", basicAttribute);

        //Map<String ,String> kvVal = new HashMap<>();
        attributes = new ArrayList<>();
        attribute = new CharacterAttribute("mission type", "string", "eq", "通信中继");
        attributes.add(attribute);
        attribute = new CharacterAttribute("mission height", "range", "in", "10000-12000m");
        attributes.add(attribute);
        attribute = new NumericAttribute("mission coverage area", "double", "ge", 20, "km");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("mission capacity", basicAttribute);


        attributes = new ArrayList<>();
        attribute = new CharacterAttribute("take off and landing requirements", "string", "eq", "无");
        attributes.add(attribute);
        attribute = new NumericAttribute("height", "double", "ge", 13, "km");
        attributes.add(attribute);
        attribute = new NumericAttribute("speed", "double", "ge", 40, "km/h");
        attributes.add(attribute);
        attribute = new NumericAttribute("flight range", "double", "ge", 200, "km");
        attributes.add(attribute);
        attribute = new NumericAttribute("endurance", "double", "ge", 100, "h");
        attributes.add(attribute);
        attribute = new NumericAttribute("flight service life", "double", "ge", 2000, "h");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("flight capacity", basicAttribute);


//        attributes = new ArrayList<>();
//        attribute = new CharacterAttribute("weight, cost and service time", "string","eq", attributes);
//        attributes.put("weight and service time", attribute);

        attributes = new ArrayList<>();
        attribute = new CharacterAttribute("single fuselage", "string","eq", "无");
        attributes.add(attribute);
        attribute = new NumericAttribute("length", "double", "ge", 25, "m");
        attributes.add(attribute);
        attribute = new NumericAttribute("wing span", "double", "ge", 10, "m");
        attributes.add(attribute);
        attribute = new NumericAttribute("aspect ratio", "double", "ge", 4, "");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("aerodynamic configuration", basicAttribute);

        attributes = new ArrayList<>();
        attribute = new CharacterAttribute("structural layout", "string","eq", "常规布局，长机翼窄机身");
        attributes.add(attribute);
        attribute = new CharacterAttribute("strength and stiffness", "string","eq", "航空强度");
        attributes.add(attribute);
        attribute = new CharacterAttribute("materials", "string","eq", "合金等");
        attributes.add(attribute);
        attribute = new CharacterAttribute("manufacturing techniques", "string","eq", "无");
        attributes.add(attribute);
        attribute = new NumericAttribute("weight", "double", "le", 50, "kg");
        attributes.add(attribute);
        attribute = new NumericAttribute("cost", "double", "le", 20000, "$");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("airframe structure", basicAttribute);

        attributes = new ArrayList<>();
        attribute = new CharacterAttribute("photovoltaic system", "string","eq", "太阳能光伏");
        attributes.add(attribute);
        attribute = new CharacterAttribute("energy storage battery", "string","eq", "大储量，高功率");
        attributes.add(attribute);
        attribute = new NumericAttribute("maximum output", "double", "ge", 20, "");
        attributes.add(attribute);
        attribute = new NumericAttribute("endurance", "double", "ge", 80, "h");
        attributes.add(attribute);
        attribute = new NumericAttribute("weight", "double", "le", 50, "kg");
        attributes.add(attribute);
        attribute = new NumericAttribute("cost", "double", "le", 5000, "$");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("energy system", basicAttribute);

        attributes = new ArrayList<>();
        attribute = new CharacterAttribute("electric motor", "string","eq", "高功率");
        attributes.add(attribute);
        attribute = new CharacterAttribute("propeller", "string","eq", "无");
        attributes.add(attribute);
        attribute = new NumericAttribute("power", "double", "ge", 10, "");
        attributes.add(attribute);
        attribute = new NumericAttribute("weight", "double", "le", 100, "kg");
        attributes.add(attribute);
        attribute = new NumericAttribute("cost", "double", "le", 10000, "$");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("propulsion system", basicAttribute);

        attributes = new ArrayList<>();
        attribute = new CharacterAttribute("communication", "string","eq", "无线电等");
        attributes.add(attribute);
        attribute = new CharacterAttribute("reconnaissance", "string","eq", "北斗卫星");
        attributes.add(attribute);
        attribute = new NumericAttribute("weight", "double", "le", 30, "kg");
        attributes.add(attribute);
        attribute = new NumericAttribute("cost", "double", "le", 6000, "$");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("mission system", basicAttribute);

        attributes = new ArrayList<>();
        attribute = new CharacterAttribute("flight control and actuation", "string","eq", "无");
        attributes.add(attribute);
        attribute = new CharacterAttribute("integrated management of energy, propulsion, and missions", "string","eq", "无");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("flight management system", basicAttribute);

        attributes = new ArrayList<>();
        attribute = new CharacterAttribute("ground station", "string","eq", "地面保障站");
        attributes.add(attribute);
        attribute = new CharacterAttribute("take off and landing", "string","eq", "起降机场");
        attributes.add(attribute);
        attribute = new CharacterAttribute("transportation and storage", "string","eq", "公路或铁路运输");
        attributes.add(attribute);
        attribute = new CharacterAttribute("inspection and maintenance", "string","eq", "检测维修专业技术人员");
        attributes.add(attribute);
        attribute = new NumericAttribute("cost", "double", "le", 4000, "$");
        attributes.add(attribute);
        basicAttribute = new BasicAttribute(attributes);
        template1.put("ground support", basicAttribute);

        template = new Template(2, template1);
        repository.save(template);

        List<Template> templates = repository.findAll();
        return templates;
    }

    @RequestMapping("/addInformation")
    public void addInformation() {
        AttrDetail attrDetail1 = new AttrDetail("double", "ge");
        AttrDetail attrDetail2 = new AttrDetail("string", "eq");
        AttrDetail attrDetail3 = new AttrDetail("range", "in");
        AttrDetail attrDetail4 = new AttrDetail("double", "le");
        AttrDetail attrDetail5 = new AttrDetail("string", "SD");
        Map<String, AttrDetail> details = new HashMap<>();
        details.put("mission type", attrDetail2);
        details.put("mission height", attrDetail3);
        details.put("mission coverage area", attrDetail3);
        AttributesInformation attrInfo = new AttributesInformation(1, "mission capacity", details);
        AttrInfoService.saveAttrInfo(attrInfo);

        details = new HashMap<>();
        details.put("height", attrDetail1);
        details.put("speed", attrDetail1);
        details.put("flight range", attrDetail1);
        details.put("endurance", attrDetail1);
        details.put("take off and landing requirements", attrDetail5);
        details.put("flight service life", attrDetail1);
        attrInfo = new AttributesInformation(2, "flight capacity", details);
        AttrInfoService.saveAttrInfo(attrInfo);

        details = new HashMap<>();
        details.put("single fuselage", attrDetail5);
//        details.put("speed", attrDetail1);
//        details.put("flight range", attrDetail1);
//        details.put("endurance", attrDetail1);
//        details.put("take off and landing requirements", attrDetail2);
//        details.put("flight service life", attrDetail1);
        details.put("length", attrDetail1);
        details.put("wing span", attrDetail1);
        details.put("aspect ratio", attrDetail1);
        attrInfo = new AttributesInformation(3, "aerodynamic configuration", details);
        AttrInfoService.saveAttrInfo(attrInfo);

        details = new HashMap<>();
        details.put("structural layout", attrDetail5);
        details.put("strength and stiffness", attrDetail5);
        details.put("materials", attrDetail5);
        details.put("manufacturing techniques", attrDetail5);
        details.put("weight", attrDetail4);
        details.put("cost", attrDetail4);
        attrInfo = new AttributesInformation(4, "airframe structure", details);
        AttrInfoService.saveAttrInfo(attrInfo);

        details = new HashMap<>();
        details.put("photovoltaic system", attrDetail5);
        details.put("energy storage battery", attrDetail5);
        details.put("endurance", attrDetail1);
        details.put("weight", attrDetail4);
        details.put("cost", attrDetail4);
        attrInfo = new AttributesInformation(5, "energy system", details);
        AttrInfoService.saveAttrInfo(attrInfo);

        details = new HashMap<>();
        details.put("electric motor", attrDetail5);
        details.put("propeller", attrDetail5);
        details.put("power", attrDetail1);
        details.put("weight", attrDetail4);
        details.put("cost", attrDetail4);
        attrInfo = new AttributesInformation(6, "propulsion system", details);
        AttrInfoService.saveAttrInfo(attrInfo);

        details = new HashMap<>();
        details.put("communication", attrDetail5);
        details.put("reconnaissance", attrDetail5);
        details.put("weight", attrDetail4);
        details.put("cost", attrDetail4);
        attrInfo = new AttributesInformation(7, "mission system", details);
        AttrInfoService.saveAttrInfo(attrInfo);

        details = new HashMap<>();
        details.put("flight control and actuation", attrDetail5);
        details.put("integrated management of energy, propulsion, and missions", attrDetail5);
        attrInfo = new AttributesInformation(8, "flight management system", details);
        AttrInfoService.saveAttrInfo(attrInfo);

        details = new HashMap<>();
        details.put("ground station", attrDetail5);
        details.put("take off and landing", attrDetail5);
        details.put("transportation and storage", attrDetail5);
        details.put("inspection and maintenance", attrDetail5);
        details.put("cost", attrDetail4);
        attrInfo = new AttributesInformation(9, "ground support", details);
        AttrInfoService.saveAttrInfo(attrInfo);



        details = new HashMap<>();
        details.put("cost", attrDetail4);
        attrInfo = new AttributesInformation(10, "cost", details);
        AttrInfoService.saveAttrInfo(attrInfo);

        details = new HashMap<>();
        details.put("endurance", attrDetail1);
        attrInfo = new AttributesInformation(11, "endurance", details);
        AttrInfoService.saveAttrInfo(attrInfo);

        details = new HashMap<>();
        details.put("loading_capacity", attrDetail1);
        attrInfo = new AttributesInformation(12, "loading_capacity", details);
        AttrInfoService.saveAttrInfo(attrInfo);
    }

//    @RequestMapping("/findTemplate")
    @QueryMapping
    public List<TemplateForReturn> find(@Argument String json)
//            throws IOException, InterruptedException
    {
//        Optional<Template> tempTemplate = repository.findById(1);
//        Template template = tempTemplate.orElse(new Template());
//        System.out.println("match is:"+template.getAttributes().get(0).getMatch());
        List<Template> templates = new ArrayList<>();
//        Attribute attribute = repository.findMatchOpByAttrName("cost");
//        String matchOp = "";
//        System.out.println("match is:"+attribute.getMatch());
//        if (Objects.equals(attribute.getMatch(), "le")){
//            matchOp = "lte";
//            System.out.println("success");
//        }
//        templates = TemplateDao.findTemplateByAttrTypeInt("cost", matchOp, 40000);
        System.out.println(json+'\n');
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> param = new HashMap<>();
        try{
            //通过工具把json格式的字符串变成map格式
        param = objectMapper.readValue(json, Map.class);
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("param:"+param);
        Map<String, BasicAttribute> matchValMap = new HashMap<>();
        double numVal;
        String strVal;
        for(String attrName : param.keySet()){
            if(Objects.equals(attrName, "cost") || Objects.equals(attrName, "endurance") || Objects.equals(attrName, "loading_capacity")){
                numVal = Double.parseDouble(param.get(attrName).toString());
                System.out.println("numVal:"+numVal);
                NumericAttribute numAttr = new NumericAttribute(attrName, "double", "", numVal, "");
                List<Attribute> attrs = new ArrayList<>();
                attrs.add(numAttr);
                BasicAttribute basicAttr = new BasicAttribute(attrs);
                matchValMap.put(attrName, basicAttr);
            }
            else{
                if(param.get(attrName) instanceof Map){
                    Map<String, Object> attrsMap = (Map<String, Object>) param.get(attrName);
                    System.out.println(attrsMap);
                    AttributesInformation attrInfo = AttrInfoService.findAttrInfo(attrName);
                    Map<String, AttrDetail> attrDetailMap = attrInfo.getAttrDetails();
                    for(String attrDetailName : attrsMap.keySet()){
                        if(Objects.equals(attrDetailMap.get(attrDetailName).getType(), "double")){
                            numVal = Double.parseDouble(attrsMap.get(attrDetailName).toString());
                            NumericAttribute numAttr = new NumericAttribute(attrDetailName, "double", "", numVal, "");
                            List<Attribute> attrs = new ArrayList<>();
                            attrs.add(numAttr);
                            BasicAttribute basicAttr = new BasicAttribute(attrs);
                            matchValMap.put(attrName, basicAttr);
                        }
                        else if(Objects.equals(attrDetailMap.get(attrDetailName).getType(), "string")){
                            strVal = attrsMap.get(attrDetailName).toString();
                            CharacterAttribute charAttr = new CharacterAttribute(attrDetailName, "string", "", strVal);
                            List<Attribute> attrs = new ArrayList<>();
                            attrs.add(charAttr);
                            BasicAttribute basicAttr = new BasicAttribute(attrs);
                            matchValMap.put(attrName, basicAttr);
                        }
                    }
                }
                else{
                    System.out.println(param.get(attrName).getClass());
                }
            }
        }
//        double numVal = Double.parseDouble(param.get("cost").toString());
//        System.out.println("numVal:"+numVal);
//        Map<String, BasicAttribute> matchValMap = new HashMap<>();
//        NumericAttribute numAttr = new NumericAttribute("cost", "double", "", numVal, "");
//        List<Attribute> attrs = new ArrayList<>();
//        attrs.add(numAttr);
//        BasicAttribute basicAttr = new BasicAttribute(attrs);
//        matchValMap.put("cost", basicAttr);
//
//        numVal = Double.parseDouble(param.get("endurance").toString());
//        numAttr = new NumericAttribute("endurance", "double", "", numVal, "");
//        attrs = new ArrayList<>();
//        attrs.add(numAttr);
//        basicAttr = new BasicAttribute(attrs);
//        matchValMap.put("endurance", basicAttr);
//
//        numVal = Double.parseDouble(param.get("loading_capacity").toString());
//        numAttr = new NumericAttribute("loading_capacity", "double", "", numVal, "");
//        attrs = new ArrayList<>();
//        attrs.add(numAttr);
//        basicAttr = new BasicAttribute(attrs);
//        matchValMap.put("loading_capacity", basicAttr);

//        String strVal = param.get("mission capacity").toString();
//        String[] parts;
//        if(strVal != null && !strVal.equals(":")) {
//             parts = strVal.split(":", -1);
////        System.out.println("Number of parts: " + parts.length);
////        System.out.println("Parts: " + Arrays.toString(parts));
//            CharacterAttribute charAttr = new CharacterAttribute("mission type", "string", "", parts[0]);
//            attrs = new ArrayList<>();
//            attrs.add(charAttr);
//            basicAttr = new BasicAttribute(attrs);
//            matchValMap.put("mission capacity", basicAttr);
//        }
//        strVal = param.get("flight capacity");
//        if(strVal != null && !strVal.equals(":::")) {
//            parts = strVal.split(":", -1);
//            numAttr = new NumericAttribute("flight service life", "double", "", Double.parseDouble(parts[3]),"");
//            attrs = new ArrayList<>();
//            attrs.add(numAttr);
//            basicAttr = new BasicAttribute(attrs);
//            matchValMap.put("flight capacity", basicAttr);
//        }
//        strVal = param.get("flight capacity").toString();
//        if(strVal != null && !strVal.equals(":::")) {
//            parts = strVal.split(":", -1);
//            CharacterAttribute charAttr = new CharacterAttribute("take off and landing requirements", "string", "", parts[2]);
//            attrs = new ArrayList<>();
//            attrs.add(charAttr);
//            basicAttr = new BasicAttribute(attrs);
//            matchValMap.put("flight capacity", basicAttr);
//        }

        System.out.println(param.get("mission capacity"));
//        System.out.println(param.get("flight capacity"));

        try{
        templates = TemplateService.findSuitTemplate(matchValMap);}
        catch (Exception e){
            System.out.println(e);
        }
        System.out.println(templates);
        List<TemplateForReturn> templateForReturns = new ArrayList<>();
        for(Template template : templates){
            Map<String, BasicAttribute> attrsMap = template.getAttributes();
            List<AttributesMap> attributesMapList = new ArrayList<>();
            for(Map.Entry<String, BasicAttribute> entry : attrsMap.entrySet()){
                attributesMapList.add(new AttributesMap(entry.getKey(), entry.getValue()));
            }
            TemplateForReturn templateForReturn = new TemplateForReturn(template.getId(), attributesMapList);
            templateForReturns.add(templateForReturn);
        }
//        return templates;
        return templateForReturns;
    }

    @RequestMapping("/test")
    public AttributesInformation test() throws IOException, InterruptedException {
//        Criteria criteria = Criteria.where("attributes")
//                .elemMatch(Criteria.where("name").is("cost").and("intVal").lte(40000));
//
//        Query query = new Query(criteria);
//
//        return mongoTemplate.find(query, Template.class);
//        Attribute attr = new BasicAttribute();
//        if(attr.getType() == "string"){
//            BasicAttribute basicAttr = (BasicAttribute) attr;
//            basicAttr.getUnit();
//        }
          return AttrInfoService.findAttrInfo("cost");
//        return repository.findMatchOpByAttrName("cost");
    }
}
