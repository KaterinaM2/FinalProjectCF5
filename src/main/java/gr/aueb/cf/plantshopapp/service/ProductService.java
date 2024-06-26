package gr.aueb.cf.plantshopapp.service;

import gr.aueb.cf.plantshopapp.dao.ProductRepository;
import gr.aueb.cf.plantshopapp.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieves all products from the repository
     * @return List of products
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a product by its id
     * @param id Product id
     * @return Product
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    /**
     * Saves a product to the repository
     * @param product Product to save
     * @return Saved product
     */
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Deletes a product by its id
     * @param id Product id
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

