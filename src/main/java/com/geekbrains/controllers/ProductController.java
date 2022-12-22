package com.geekbrains.controllers;


import com.geekbrains.model.Product;
import com.geekbrains.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public List<Product> findAllProduct(){
        return productService.findAll();

    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Long id){
        return productService.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @PostMapping("/add")
    public void addProduct (@RequestParam String name,@RequestParam BigDecimal price,@RequestParam String manufacturer){
        productService.save(name,price,manufacturer);
    }

    @GetMapping("/delete/{id}")
    public void deleteProductById(@PathVariable Long id){
        productService.deleteById(id);
    }



}
