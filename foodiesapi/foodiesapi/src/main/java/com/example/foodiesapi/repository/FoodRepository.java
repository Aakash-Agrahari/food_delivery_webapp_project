package com.example.foodiesapi.repository;

import com.example.foodiesapi.entity.FoodEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface FoodRepository extends MongoRepository<FoodEntity, String> {

}
