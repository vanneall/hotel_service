package org.example.kitchen.repository;

import lombok.Getter;
import org.example.kitchen.data.Dish;
import org.example.kitchen.data.Order;
import org.example.kitchen.errors.exception.NotFoundException;
import org.example.kitchen.errors.exception.StateConflictException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Getter
@Repository
public class OrderRepository {
    private final List<Order> orders = new ArrayList<>();
    private final List<Long> chefs = new ArrayList<>();

    {
        orders.add(new Order(1l, 1l, null, "2024-11-14", false, null, Order.OrderStatus.CREATED, List.of(new Dish(1l, 3, null))));
        orders.add(new Order(2l, 1l, null, "2024-11-14", false, null, Order.OrderStatus.CREATED, List.of(new Dish(1l, 3, null))));
        orders.add(new Order(3l, 1l, null, "2024-11-14", false, null, Order.OrderStatus.CREATED, List.of(new Dish(1l, 3, null))));

        chefs.add(1l);
        chefs.add(2l);
        chefs.add(3l);
    }

    public List<Order> getOrdersPaging(final int offset, final int limit) {
        return orders.stream().skip(offset).limit(limit).toList();
    }

    public void createOrder(Order order) {
        orders.add(order);
    }

    public void cancelOrder(long orderId) throws RuntimeException {
        Order order = findOrderById(orderId);
        if (order.getStatus() != Order.OrderStatus.CREATED) {
            throw new StateConflictException("Order can only be canceled if it is in CREATED status.");
        }
        order.setStatus(Order.OrderStatus.CANCELED);
    }

    public void setOrderInProcess(long orderId, long chefId) throws RuntimeException {
        if (!chefs.contains(chefId)) {
            throw new NotFoundException("Chef with id " + chefId + " not found.");
        }

        Order order = findOrderById(orderId);
        if (order.getStatus() != Order.OrderStatus.CREATED) {
            throw new StateConflictException("Order status must be CREATED to move to IN_PROCESS.");
        }

        order.setStatus(Order.OrderStatus.IN_PROCESS);
        order.setChefId(chefId);
    }

    public void setOrderDone(long orderId) throws RuntimeException {
        Order order = findOrderById(orderId);
        if (order.getStatus() != Order.OrderStatus.IN_PROCESS) {
            throw new StateConflictException("Order status must be IN_PROCESS to move to DONE.");
        }
        order.setStatus(Order.OrderStatus.DONE);
    }

    private Order findOrderById(long orderId) throws NotFoundException {
        return orders.stream()
            .filter(order -> order.getOrderId() == orderId)
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Order with id " + orderId + " not found."));
    }
}
