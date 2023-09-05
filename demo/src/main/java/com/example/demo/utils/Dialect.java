package com.example.demo.utils;

import org.hibernate.dialect.MySQLDialect;

import java.sql.Types;

public class Dialect extends MySQLDialect {
    public Dialect(){
        super();
        registerColumnType(Types.NULL, "null");
        registerHibernateType(Types.NULL,"null");
    }
}
