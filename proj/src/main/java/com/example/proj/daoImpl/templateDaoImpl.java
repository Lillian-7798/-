package com.example.proj.daoImpl;

import com.example.proj.dao.templateDao;
import com.example.proj.entity.*;
import com.example.proj.repository.templateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class templateDaoImpl implements templateDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private templateRepository TemplateRepository;

    @Override
    public Attribute findMatchOpByAttrName(String attrName){
        return TemplateRepository.findMatchOpByAttrName(attrName);
    }

    @Override
    public List<Template> findSuitTemplate(Map<String, BasicAttribute> matchAttrMap){
        Criteria criteria = null;
        for(String attrName : matchAttrMap.keySet()){
            BasicAttribute basicAttribute = matchAttrMap.get(attrName);
            for(Attribute attr : basicAttribute.getAttrs()){
                String matchOp = attr.getMatch();
                System.out.println("match is:" + matchOp);
                if(Objects.equals(matchOp, "eq")){
                    if(Objects.equals(attr.getType(), "double")){
                        NumericAttribute numAttr = (NumericAttribute)attr;
                        Criteria tempCriteria = Criteria.where("attributes."+attrName+".attrs")
                                .elemMatch(Criteria.where("name").is(numAttr.getName()).and("numVal").is(numAttr.getNumVal()));
                        if (criteria != null)
                            criteria = new Criteria().andOperator(criteria, tempCriteria);
                        else
                            criteria = tempCriteria;
                    }
                    else if(Objects.equals(attr.getType(), "string")){
                        CharacterAttribute charAttr = (CharacterAttribute)attr;
                        Criteria tempCriteria = Criteria.where("attributes."+attrName+".attrs")
                                .elemMatch(Criteria.where("name").is(charAttr.getName()).and("strVal").is(charAttr.getStrVal()));
                        if (criteria != null)
                            criteria = new Criteria().andOperator(criteria, tempCriteria);
                        else
                            criteria = tempCriteria;
                    }
                }
                else if(Objects.equals(matchOp, "ge")){
                    if(!Objects.equals(attr.getType(), "double")){
                        System.out.println("error!");
                        return new ArrayList<>();
                    }
                    NumericAttribute numAttr = (NumericAttribute)attr;
                    Criteria tempCriteria = Criteria.where("attributes."+attrName+".attrs")
                            .elemMatch(Criteria.where("name").is(numAttr.getName()).and("numVal").gte(numAttr.getNumVal()));
                    if (criteria != null)
                        criteria = new Criteria().andOperator(criteria, tempCriteria);
                    else
                        criteria = tempCriteria;
                }
                else if(Objects.equals(matchOp, "le")){
                    if(!Objects.equals(attr.getType(), "double")){
                        System.out.println("error!");
                        return new ArrayList<>();
                    }
                    NumericAttribute numAttr = (NumericAttribute)attr;
                    Criteria tempCriteria = Criteria.where("attributes."+attrName+".attrs")
                            .elemMatch(Criteria.where("name").is(numAttr.getName()).and("numVal").lte(numAttr.getNumVal()));
                    if (criteria != null)
                        criteria = new Criteria().andOperator(criteria, tempCriteria);
                    else
                        criteria = tempCriteria;
                }
            }
        }

        assert criteria != null;
        Query query = new Query(criteria);
        return mongoTemplate.find(query, Template.class);
    }

    @Override
    public List<Template> findTemplateByAttrTypeInt(String attrName, String matchOp, Integer val){
        System.out.println(attrName+matchOp);
        Criteria criteria = Criteria.where("attributes")
                .elemMatch(Criteria.where("name").is(attrName).and("intVal").lte(val));

        Query query = new Query(criteria);

        return mongoTemplate.find(query, Template.class);
    }

    @Override
    public List<Template> findAll(){
        return TemplateRepository.findAll();
    }
}
