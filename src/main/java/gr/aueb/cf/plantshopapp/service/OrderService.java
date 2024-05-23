package gr.aueb.cf.plantshopapp.service;

import gr.aueb.cf.plantshopapp.dao.OrderRepository;
import gr.aueb.cf.plantshopapp.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Retrieves all orders from the repository.
     * @return List of orders
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Retrieves an order by its ID.
     * @param id Order ID
     * @return Order
     */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    /**
     * Saves an order to the repository.
     * @param order Order to save
     * @return Saved order
     */
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    /**
     * Deletes an order by its ID.
     * @param id Order ID
     */
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}

