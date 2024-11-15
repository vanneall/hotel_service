package org.example.hotel_service.kitchen.service;

import lombok.AllArgsConstructor;
import org.example.hotel_service.errors.exception.NotFoundException;
import org.example.hotel_service.kitchen.data.Order;
import org.example.hotel_service.kitchen.data.entity.DishEntity;
import org.example.hotel_service.kitchen.data.request.DishRequest;
import org.example.hotel_service.kitchen.data.request.OrderRequest;
import org.example.hotel_service.kitchen.repository.DishRepository;
import org.example.hotel_service.kitchen.repository.OrderRepository;
import org.example.hotel_service.utils.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;

    public List<Order> getOrders(final int offset, final int limit) {
        return orderRepository.getOrdersPaging(offset, limit);
    }

    public void createOrder(OrderRequest orderRequest) throws NotFoundException {
        boolean isExistsAllDishes = dishRepository.getDishes().stream()
            .map(DishEntity::getId)
            .collect(Collectors.toSet())
            .containsAll(orderRequest.getDishes().stream()
                .map(DishRequest::getDishId)
                .collect(Collectors.toSet()));
        if (!isExistsAllDishes) throw new NotFoundException("Dish not found");

        orderRepository.createOrder(OrderMapper.map(orderRequest));
    }

    public void cancelOrder(final int orderId, final String reason) throws RuntimeException {
        orderRepository.cancelOrder(orderId);
    }

    public void setProgressOrder(final int orderId, final long chefId) throws RuntimeException {
        orderRepository.setOrderInProcess(orderId, chefId);
    }

    public void setDoneOrder(final int orderId) throws RuntimeException {
        orderRepository.setOrderDone(orderId);
    }
}
