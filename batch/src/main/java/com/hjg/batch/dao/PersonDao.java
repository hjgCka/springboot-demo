package com.hjg.batch.dao;

import com.hjg.batch.model.Person;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/9/22
 */
public interface PersonDao {

    Person findPerson(String name);
}
