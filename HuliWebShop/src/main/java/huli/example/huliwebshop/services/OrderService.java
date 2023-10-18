package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.OrderDTO;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;


public interface OrderService {
  ResponseEntity<String> placeOrder(Principal principal, String paymentMethod);
  boolean isValidPaymentMethod(String paymentMethod);
  ResponseEntity<String> updateOrderStatus(Long orderId, String newStatus);
  List<OrderDTO> getUserOrders(Principal principal);

}



