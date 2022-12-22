package com.geekbrains.services;

import com.geekbrains.model.Product;
import com.geekbrains.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> findAll(){
       return productRepository.findAll();
    }

    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }

    public void save(String name, BigDecimal price, String manufacturer) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setManufacturer(manufacturer);
        productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
