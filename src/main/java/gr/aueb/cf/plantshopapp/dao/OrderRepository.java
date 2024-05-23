package gr.aueb.cf.plantshopapp.dao;


import gr.aueb.cf.plantshopapp.dto.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

