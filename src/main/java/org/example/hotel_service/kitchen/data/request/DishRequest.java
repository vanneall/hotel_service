package org.example.hotel_service.kitchen.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DishRequest {
    Long dishId;
    Integer quantity;
    String commentary;
}
