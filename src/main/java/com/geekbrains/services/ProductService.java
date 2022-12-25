package com.geekbrains.services;

import com.geekbrains.exceptions.ResourceNotFoundException;
import com.geekbrains.model.Product;
import com.geekbrains.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
       return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> findMinMaxPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice == null) minPrice = findProductMinPrice().orElseThrow().getPrice();
        if (maxPrice == null) maxPrice = findProductMaxPrice().orElseThrow().getPrice();
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }


    public Optional<Product> findProductMaxPrice() {
        return productRepository.findProductByMaxPrice();
    }

    public Optional<Product> findProductMinPrice() {
        return productRepository.findProductByMinPrice();
    }

    @Transactional
    public void changePrice(Long id, BigDecimal delta) {
        Product product =  productRepository.findById(id).orElseThrow(()-> new  ResourceNotFoundException("Невозможно изменить цену продукта,продукт не найден! ID:"+id));
        product.setPrice(product.getPrice().add(delta));
    }
}
