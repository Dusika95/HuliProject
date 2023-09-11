package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.OrderDTO;

public interface OrderService {
    Long createOrder(OrderDTO orderDTO);
}
