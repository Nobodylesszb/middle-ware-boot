package com.bo.springboot.dao;

import com.bo.springboot.domain.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDao extends MongoRepository<Car, Integer> {
}
