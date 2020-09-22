package com.hjg.batch.dao.impl;

import com.hjg.batch.dao.PersonDao;
import com.hjg.batch.model.Person;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/9/22
 */
@Repository
public class PersonDaoImpl implements PersonDao {

    @Override
    public Person findPerson(String name) {
        System.out.println("name = " + name);
        Person person = new Person();
        person.setName(name);
        person.setAge(23);
        person.setAddress("China Beijing");
        return person;
    }
}
