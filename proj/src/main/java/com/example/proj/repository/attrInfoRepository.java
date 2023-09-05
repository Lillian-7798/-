package com.example.proj.repository;

import com.example.proj.entity.AttributesInformation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface attrInfoRepository extends MongoRepository<AttributesInformation, Integer> {
    AttributesInformation findByAttrName(@Param("attrName") String attrName);
}
