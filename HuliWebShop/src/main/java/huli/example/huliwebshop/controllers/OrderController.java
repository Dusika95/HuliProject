package huli.example.huliwebshop.controllers;

import huli.example.huliwebshop.DTOs.OrderDTO;
import huli.example.huliwebshop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestBody Map<String, String> requestBody, Principal principal) {
        String paymentMethod = requestBody.get("paymentMethod");
        ResponseEntity<String> response = orderService.placeOrder(principal, paymentMethod);
        return response;
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/status/{orderId}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestBody Map<String, String> requestBody) {
        String newStatus = requestBody.get("newStatus");
        ResponseEntity<String> response = orderService.updateOrderStatus(orderId, newStatus);
        return response;
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDTO>> getUserOrders(Principal principal) {
        List<OrderDTO> userOrders = orderService.getUserOrders(principal);
        return ResponseEntity.ok(userOrders);
    }
}

