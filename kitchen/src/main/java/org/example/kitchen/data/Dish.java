package org.example.kitchen.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Dish {
    Long dishId;
    Integer quantity;
    String commentary;
}
