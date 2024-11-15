package org.example.hotel_service.utils.mapper;

import org.example.hotel_service.kitchen.data.Dish;
import org.example.hotel_service.kitchen.data.Order;
import org.example.hotel_service.kitchen.data.request.OrderRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class OrderMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static public Order map(OrderRequest orderRequest) {
        return new Order(
            new Random().nextLong(),
            orderRequest.getUserId(),
            orderRequest.getCommentary(),
            LocalDateTime.now().format(formatter),
            orderRequest.getDelivery(),
            null,
            Order.OrderStatus.CREATED,
            orderRequest.getDishes().stream()
                .map(it -> new Dish(it.getDishId(), it.getQuantity(), it.getCommentary()))
                .toList()
        );
    }
}
