package org.example.kitchen.repository;

import lombok.Getter;
import org.example.kitchen.data.entity.DishEntity;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Getter
@Repository
public class DishRepository {
    private Set<DishEntity> dishes = new HashSet<>();

    {
        dishes.add(new DishEntity(1L));
        dishes.add(new DishEntity(2L));
        dishes.add(new DishEntity(3L));
        dishes.add(new DishEntity(4L));
        dishes.add(new DishEntity(5L));
        dishes.add(new DishEntity(6L));
    }
}
