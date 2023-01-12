package com.geekbrains.services;

import com.geekbrains.dto.ProductDto;
import com.geekbrains.repository.CartRepository;
import com.geekbrains.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
   private final CartRepository cartRepository;
    private final ProductRepository productRepository;


    public List<ProductDto> getProductListInCart(){
       return cartRepository.getProductListInCart();
   }

    public  void  add(ProductDto productDto){
        if (cartRepository.getProductListInCart().contains(productDto)) return;
       cartRepository.add(productDto);
    }


    public void remove(ProductDto productDto){
        cartRepository.remove(productDto);
    }




}
