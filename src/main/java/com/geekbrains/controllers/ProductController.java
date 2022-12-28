package com.geekbrains.controllers;


import com.geekbrains.dto.ProductDto;
import com.geekbrains.exceptions.ResourceNotFoundException;
import com.geekbrains.model.Product;
import com.geekbrains.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;


@Slf4j
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<ProductDto> findAllProduct(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "min_price", required = false) BigDecimal minPrice,
            @RequestParam(name = "max_price", required = false) BigDecimal maxPrice,
            @RequestParam(name = "name_part", required = false) String namePart
    ) {
        log.info(String.format("%nLogParam - Page: %s%n minPrice: %f%n maxPrice: %f%n namePart: %s%n", page, minPrice, maxPrice, namePart));
        if (page < 1) {
            page = 1;
        }
        return productService.find(minPrice, maxPrice, namePart, page).map(ProductDto::new);
    }

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        return productService.findById(id).map(ProductDto::new).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));

    }

    // ProductDto?
    @PostMapping
    public Product saveNewProduct(@RequestBody Product product) {
        product.setId(null);
        log.info(product.toString());
        return productService.save(product);
    }

    //  ProductDto?
    @PutMapping
    public Product updateProduct(@RequestBody Product student) {
        return productService.save(student);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }


    @PutMapping("/change_price")
    public void changePrice(@RequestParam Long id, @RequestParam BigDecimal delta){
        productService.changePrice(id, delta);

    }


}
