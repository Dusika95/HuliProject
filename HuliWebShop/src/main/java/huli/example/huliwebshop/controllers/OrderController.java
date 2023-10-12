package huli.example.huliwebshop.controllers;

import huli.example.huliwebshop.DTOs.OrderDTO;
import huli.example.huliwebshop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place/{userId}")
    public ResponseEntity<String> placeOrder(@PathVariable Long userId, @RequestBody Map<String, String> requestBody) {
        String paymentMethod = requestBody.get("paymentMethod");
        ResponseEntity<String> response = orderService.placeOrder(userId, paymentMethod);
        return response;
    }

    @PutMapping("/status/{orderId}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestBody Map<String, String> requestBody) {
        String newStatus = requestBody.get("newStatus");
        ResponseEntity<String> response = orderService.updateOrderStatus(orderId, newStatus);
        return response;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getUserOrders(@PathVariable Long userId) {
        List<OrderDTO> userOrders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(userOrders);
    }
}

