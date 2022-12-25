package com.geekbrains.controllers;


import com.geekbrains.exceptions.ResourceNotFoundException;
import com.geekbrains.model.Product;
import com.geekbrains.services.ProductService;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public List<Product> findAllProduct() {
        return productService.findAllProducts();
    }
    @PostMapping("")
    public  Product saveNewProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Long id) {
        return productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));

    }

    @GetMapping("/delete/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }


    @GetMapping("/change_price")
    public void changePrice(@RequestParam Long id, @RequestParam BigDecimal delta){
        productService.changePrice(id, delta);

    }


    @GetMapping("/find_price/{minPrice}&{maxPrice}")
    public List<Product> findMinBetweenMaxPrice(@PathVariable(required = false) BigDecimal minPrice,
                                                @PathVariable(required = false) BigDecimal maxPrice) {
        return productService.findMinMaxPrice(minPrice, maxPrice);
    }



    @GetMapping("/find_price")
    public List<Product> findMinBetweenMaxPriceFilter(@RequestParam(required = false) BigDecimal minPrice,
                                                      @RequestParam(required = false) BigDecimal maxPrice) {
        return productService.findMinMaxPrice(minPrice, maxPrice);
    }


}
