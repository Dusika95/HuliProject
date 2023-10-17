package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.OrderDTO;
import huli.example.huliwebshop.DTOs.OrderItemDTO;
import huli.example.huliwebshop.DTOs.UserDTO;
import huli.example.huliwebshop.models.Cart;
import huli.example.huliwebshop.models.Order;
import huli.example.huliwebshop.models.Product;
import huli.example.huliwebshop.models.User;
import huli.example.huliwebshop.repository.ICartRepository;
import huli.example.huliwebshop.repository.IOrderRepository;
import huli.example.huliwebshop.repository.IProductRepository;
import huli.example.huliwebshop.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final IOrderRepository orderRepository;
    private final ICartRepository cartRepository;
    private final IProductRepository productRepository;
    private final IUserRepository userRepository;

    @Autowired
    public OrderServiceImpl(IOrderRepository orderRepository, ICartRepository cartRepository, IProductRepository productRepository, IUserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<String> placeOrder(Principal principal, String paymentMethod) {

        String authenticatedUserEmail = principal.getName();

        User authenticatedUser = userRepository.findByEmail(authenticatedUserEmail);

        if (authenticatedUser == null) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        if (!authenticatedUser.getEmail().equals(authenticatedUserEmail)) {
            return ResponseEntity.badRequest().body("You can only place orders for your own account.");
        }

        Cart cart = cartRepository.findByUserId(authenticatedUser.getId());

        if (cart == null) {
            return ResponseEntity.badRequest().body("Cart does not exist for this user. Cannot place an order.");
        }

        Map<Product, Integer> cartEntries = cart.getCartEntries();

        if (cartEntries.isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty. Cannot place an order.");
        }

        if (paymentMethod == null || paymentMethod.isEmpty()) {
            return ResponseEntity.badRequest().body("Payment method is missing in the request body.");
        }

        if (!isValidPaymentMethod(paymentMethod)) {
            return ResponseEntity.badRequest().body("Invalid payment method.");
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        Map<Long, Integer> orderItems = new HashMap<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Map.Entry<Product, Integer> entry : cartEntries.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            orderItems.put(product.getId(), quantity);

            double productPrice = product.getPrice();
            double totalProductPrice = productPrice * quantity;

            totalPrice = totalPrice.add(BigDecimal.valueOf(totalProductPrice));
        }

        order.setPaymentMethod(paymentMethod);

        User user = cart.getUser();
        String address = user.getAddress();
        String zipCode = user.getZipCode();
        String city = user.getCity();

        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("Placed");
        order.setShippingAddress(address);
        orderRepository.save(order);

        cartEntries.clear();
        cartRepository.save(cart);

        UserDTO userDTO = new UserDTO(user.getName(), user.getEmail(), address, zipCode, city);

        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : orderItems.entrySet()) {
            Product product = productRepository.findById(entry.getKey()).orElse(null);
            if (product != null) {
                double productPrice = product.getPrice();
                int quantity = entry.getValue();
                double totalProductPrice = productPrice * quantity;

                OrderItemDTO orderItemDTO = new OrderItemDTO(product.getId(), product.getName(), quantity, totalProductPrice);
                orderItemDTOs.add(orderItemDTO);
            }
        }

        OrderDTO orderDTO = new OrderDTO(order.getId(), userDTO, address, paymentMethod, totalPrice, "Placed", order.getOrderDate(), orderItemDTOs);

        return ResponseEntity.ok("Order is created with order id " + order.getId());
    }

    @Override
    public boolean isValidPaymentMethod(String paymentMethod) {
        List<String> validPaymentMethods = List.of("credit_card", "paypal", "cod", "bank_transfer");
        return validPaymentMethods.contains(paymentMethod);
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            return ResponseEntity.badRequest().body("Order not found.");
        }


        Map<String, Set<String>> validStatusTransitions = new HashMap<>();
        validStatusTransitions.put("Placed", new HashSet<>(Arrays.asList("Processing", "Cancelled")));
        validStatusTransitions.put("Processing", new HashSet<>(Arrays.asList("Shipped", "Cancelled")));
        validStatusTransitions.put("Shipped", new HashSet<>(Arrays.asList("Delivered")));
        validStatusTransitions.put("Delivered", new HashSet<>(Arrays.asList("Completed", "Returned")));
        validStatusTransitions.put("On Hold", new HashSet<>(Arrays.asList("Cancelled", "Processing")));
        validStatusTransitions.put("Pending Payment", new HashSet<>(Arrays.asList("Completed", "Cancelled")));
        validStatusTransitions.put("Cancelled", new HashSet<>());
        validStatusTransitions.put("Refunded", new HashSet<>());
        validStatusTransitions.put("Returned", new HashSet<>(Arrays.asList("Processing", "Cancelled")));
        validStatusTransitions.put("Completed", new HashSet<>());

        if (!validStatusTransitions.containsKey(order.getOrderStatus())) {
            return ResponseEntity.badRequest().body("Invalid current status.");
        }

        if (!validStatusTransitions.get(order.getOrderStatus()).contains(newStatus)) {
            return ResponseEntity.badRequest().body("Invalid status transition.");
        }

        order.setOrderStatus(newStatus);
        orderRepository.save(order);

        return ResponseEntity.ok("Order status updated to " + newStatus);
    }

    @Override
    @Transactional
    public List<OrderDTO> getUserOrders(Principal principal) {
        String authenticatedUserEmail = principal.getName();

        User authenticatedUser = userRepository.findByEmail(authenticatedUserEmail);
        if (authenticatedUser == null) {
            return Collections.emptyList();
        }

        List<Order> userOrders = orderRepository.findByUserId(authenticatedUser.getId());
        List<OrderDTO> orderDTOs = new ArrayList<>();

        for (Order order : userOrders) {
            if (order.getUser().getEmail().equals(authenticatedUserEmail)) {
                User user = order.getUser();
                UserDTO userDTO = new UserDTO(user.getName(), user.getEmail(), order.getShippingAddress(), user.getZipCode(), user.getCity());
                List<OrderItemDTO> orderItemDTOs = new ArrayList<>();

                for (Map.Entry<Long, Integer> entry : order.getOrderItems().entrySet()) {
                    Product product = productRepository.findById(entry.getKey()).orElse(null);
                    if (product != null) {
                        double productPrice = product.getPrice();
                        int quantity = entry.getValue();
                        double totalProductPrice = productPrice * quantity;

                        OrderItemDTO orderItemDTO = new OrderItemDTO(product.getId(), product.getName(), quantity, totalProductPrice);
                        orderItemDTOs.add(orderItemDTO);
                    }
                }

                OrderDTO orderDTO = new OrderDTO(
                        order.getId(),
                        userDTO,
                        order.getShippingAddress(),
                        order.getPaymentMethod(),
                        order.getTotalPrice(),
                        order.getOrderStatus(),
                        order.getOrderDate(),
                        orderItemDTOs
                );

                orderDTOs.add(orderDTO);
            }
        }

        return orderDTOs;
    }

}





