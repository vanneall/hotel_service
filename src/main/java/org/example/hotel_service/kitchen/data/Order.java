package org.example.hotel_service.kitchen.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Order {
    Long orderId;
    Long userId;
    String commentary;
    String timestamp;
    Boolean delivery;
    Long chefId;
    OrderStatus status;
    List<Dish> dishes;

    public enum OrderStatus {
        CREATED,
        IN_PROCESS,
        DONE,
        CANCELED,
    }
}
