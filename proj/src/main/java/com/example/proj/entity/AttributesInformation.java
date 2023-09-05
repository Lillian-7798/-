package com.example.proj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "attributes")
public class AttributesInformation {
    @Id
    private Integer id;
    private String attrName;
    private Map<String, AttrDetail> attrDetails;

    public AttributesInformation(String attrName, Map<String, AttrDetail> attrDetails){
        this.attrName = attrName;
        this.attrDetails = attrDetails;
    }
}
