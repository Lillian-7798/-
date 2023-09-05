package com.example.proj.repository;

import com.example.proj.entity.Attribute;
import com.example.proj.entity.Template;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface templateRepository extends MongoRepository<Template, Integer> {
//    @Aggregation(pipeline = {
//            "{$match: {\"attributes.name\": \"?0\"}}",
//            "{$project: {match: {$filter: {input: \"$attributes\", cond: {$eq: [\"$$this.name\", \"?0\"]}}}}}",
//            "{$limit: 1}",
//            "{ $project: { firstMatch: { $arrayElemAt: [ \"$match\", 0 ] } } },",
//            "{ $replaceRoot: { newRoot: \"$firstMatch\" } }"
//    })
    @Aggregation(pipeline = {
            "{$match: {\"attributes.?0\": { $exists: true }}}",
            "{$project: {_id: 0, match: \"$attributes.?0\"}}",
            "{$limit: 1}",
            "{ $replaceRoot: { newRoot: \"$match\" } }"
    })
    Attribute findMatchOpByAttrName(@Param("attrName") String attrName);

    @Query("{'attributes': { $elemMatch: { 'name': ?0, 'intVal': { $lte: ?1 } } }}")
    List<Template> findTemplateByOneAttrTypeIntLe(@Param("attrName") String attrName, @Param("val") Integer val);

}
