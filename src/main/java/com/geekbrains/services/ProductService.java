package com.geekbrains.services;

import com.geekbrains.dto.ProductDto;
import com.geekbrains.exceptions.ResourceNotFoundException;
import com.geekbrains.model.Product;
import com.geekbrains.repository.ProductRepository;
import com.geekbrains.repository.specifications.ProductSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<Product> find(BigDecimal minPrice, BigDecimal maxPrice, String partName, Integer page) {
        Specification spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (partName != null) {
            spec = spec.and(ProductSpecifications.nameLike(partName));
        }


        return productRepository.findAll(spec, PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.ASC, "Id")));
    }


    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
//        Product product = new Product(productDto.getName(), productDto.getPrice(), productDto.getManufacturer());
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }


    @Transactional
    public void changePrice(Long id, BigDecimal delta) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Невозможно изменить цену продукта,продукт не найден! ID:" + id));
        productRepository.updatePriceById(product.getPrice().add(delta),id);
    }
}
