package org.example.kitchen.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.kitchen.data.Order;
import org.example.kitchen.data.request.CancelRequest;
import org.example.kitchen.data.request.OrderRequest;
import org.example.kitchen.data.request.PrepareRequest;
import org.example.kitchen.errors.exception.BadRequestException;
import org.example.kitchen.service.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/kitchen")
public class KitchenController {

    private final OrderService orderService;

    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getOrders(
        @RequestParam("limit") final int limit,
        @RequestParam("offset") final int offset,
        @RequestParam(value = "status", required = false) final String status
    ) throws Exception {
        if (limit < 0 || offset < 0) throw new BadRequestException("Wrong value of 'page' or 'limit'");
        return orderService.getOrders(offset, limit);
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody final OrderRequest orderRequest) throws Exception {
        orderService.createOrder(orderRequest);
    }

    @PatchMapping("/orders/{orderId}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(
        @PathVariable("orderId") final int orderId,
        @RequestBody final CancelRequest reasonRequest
    ) throws Exception {
        orderService.cancelOrder(orderId, reasonRequest.reason());
    }

    @PatchMapping("/orders/{orderId}/prepare")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void prepareOrder(
        @PathVariable("orderId") final int orderId,
        @RequestBody final PrepareRequest prepareRequest
    ) throws Exception {
        orderService.setProgressOrder(orderId, prepareRequest.chefId());
    }

    @PatchMapping("/orders/{orderId}/complete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void completeOrder(@PathVariable("orderId") final int orderId) throws Exception {
//        orderService.setDoneOrder(orderId);
        rabbitTemplate.convertAndSend("myQueue", new TaskRequest("title", "desc", "executor1"));
    }
}
