package com.example.proj.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterAttribute extends Attribute{
    private String strVal;
    public CharacterAttribute(String name, String type, String match, Map<String, String> kvVal){
        super(name, type, match);
    }

    public CharacterAttribute(String name, String type, String match, String strVal){
        super(name, type, match);
        this.strVal = strVal;
    }
}
