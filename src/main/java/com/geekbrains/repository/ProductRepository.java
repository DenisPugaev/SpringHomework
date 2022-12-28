package com.geekbrains.repository;

import com.geekbrains.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {


    @Transactional
    @Modifying
    @Query("update Product p set p.price = ?1 where p.id=?2")
    void updatePriceById(BigDecimal price, Long id);

    List<Product> findByPriceAfter(BigDecimal price);

    List<Product> findByPriceBefore(BigDecimal price);

    List<Product> findByPriceBetween(BigDecimal priceStart, BigDecimal priceEnd);

    @Query("select p from Product p where p.price = (select max(pp.price)from Product pp) ")
    Optional<Product> findProductByMaxPrice();

    @Query("select p from Product p where p.price = (select min(pp.price)from Product pp) ")
    Optional<Product> findProductByMinPrice();

}
