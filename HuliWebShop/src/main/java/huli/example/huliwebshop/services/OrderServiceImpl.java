package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.OrderDTO;
import huli.example.huliwebshop.models.Order;
import huli.example.huliwebshop.models.OrderProduct;
import huli.example.huliwebshop.models.Product;
import huli.example.huliwebshop.models.User;
import huli.example.huliwebshop.repository.IOrderRepository;
import huli.example.huliwebshop.repository.IProductRepository;
import huli.example.huliwebshop.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IProductRepository iProductRepository;

    @Autowired
    private IOrderRepository iOrderRepository;

    @Override
    public Long createOrder(OrderDTO orderDTO) {
        User user = iUserRepository.findById(orderDTO.getUserId()).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + orderDTO.getUserId());
        }

        Set<OrderProduct> products = new HashSet<>();
        /*for (Long productId : orderDTO.getProductIds()) {
            Product product = iProductRepository.findById(productId).orElse(null);
            if (product != null) {
                products.add(product);
            }
        }*/

        if (products.isEmpty()) {
            throw new IllegalArgumentException("No valid products found in the order");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderProducts(products);
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setPaymentMethod(orderDTO.getPaymentMethod());

        Order savedOrder = iOrderRepository.save(order);

        return savedOrder.getId();
    }
}