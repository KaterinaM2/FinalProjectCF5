package gr.aueb.cf.plantshopapp.dao;

import gr.aueb.cf.plantshopapp.dto.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
