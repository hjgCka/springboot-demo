package com.hjg.mongodb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class MongoAggregateTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void aggregationTest() {
        //需要AggregateOperation 数组作为参数
        Aggregation aggregation = Aggregation.newAggregation();

        String collectionName = "";
        AggregationResults<Object> result = this.mongoTemplate.aggregate(aggregation, collectionName, Object.class);
    }
}
