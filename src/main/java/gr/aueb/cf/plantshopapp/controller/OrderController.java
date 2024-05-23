package gr.aueb.cf.plantshopapp.controller;

import gr.aueb.cf.plantshopapp.dto.Order;
import gr.aueb.cf.plantshopapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Retrieves all orders.
     * @return ResponseEntity with list of orders
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    /**
     * Retrieves an order by ID.
     * @param id Order ID
     * @return ResponseEntity with the order
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    /**
     * Saves an order.
     * @param order Order details
     * @return ResponseEntity with the saved order
     */
    @PostMapping
    public ResponseEntity<Order> saveOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.saveOrder(order));
    }

    /**
     * Deletes an order by ID.
     * @param id Order ID
     * @return ResponseEntity with status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}
