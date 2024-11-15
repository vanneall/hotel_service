package org.example.hotel_service.kitchen.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderRequest {
    Long userId;
    String commentary;
    Boolean delivery;
    List<DishRequest> dishes;
}
