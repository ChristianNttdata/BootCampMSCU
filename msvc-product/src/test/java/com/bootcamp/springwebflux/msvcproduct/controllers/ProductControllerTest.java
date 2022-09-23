package com.bootcamp.springwebflux.msvcproduct.controllers;

import com.bootcamp.springwebflux.msvcproduct.models.documents.Product;
import com.bootcamp.springwebflux.msvcproduct.repository.ProductRepository;
import com.bootcamp.springwebflux.msvcproduct.services.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductControllerTest {
    @Mock
    private ProductRepository oProductRepository;
    private WebTestClient oWebTestProduct;

    private Product oProduct = new Product();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        oProduct = new Product();
        oProduct.setId(new String());
        oProduct.setName("Producto1");
        oProduct.setType("Tipo1");
        oProduct.setCategory("Categoria1");
        oProduct.setCommission(1.00);
        oProduct.setLimitDeposit(1.00);
        oProduct.setLimitWithdrawal(1.00);
        oProduct.setLine(1.00);
        oProduct.setCreateAt(new Date());

    }

    @Test
    void addProduct() {
        //when(oProductRepository.save(any(oProduct.class))).thenReturn(Mono.just(oProduct));
        //oWebTestProduct.post()
          //      .uri("/api/clients")
            //    .contentType(MediaType.APPLICATION_JSON)
              //  .body(BodyInserters.fromObject(oProduct))
                //.exchange()
                //.expectStatus().isCreated();
        //verify(oProductRepository,times(1)).save(oProduct);
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void findProductById() {
        //when(oProductRepository.findAll()).thenReturn(Flux.just(oProduct));
        //oWebTestProduct.get()
          //      .uri("/api/clients")
            //    .exchange()
              //  .expectStatus().isOk()
                //.expectBody()
        //        .jsonPath("$.Id").isNotEmpty()
          //      .jsonPath("$.Id").isEqualTo(oProduct.getId())
            //    .jsonPath("$.Name").isEqualTo(oProduct.getName())
              //  .jsonPath("$.Type").isEqualTo(oProduct.getType())
                //.jsonPath("$.Category").isEqualTo(oProduct.getCategory())
       //         .jsonPath("$.Commission").isEqualTo(oProduct.getCommission())
         //       .jsonPath("$.LimitDeposit").isEqualTo(oProduct.getLimitDeposit())
           //     .jsonPath("$.LimitWithdrawal").isEqualTo(oProduct.getLimitWithdrawal())
             //   .jsonPath("$.Line").isEqualTo(oProduct.getLine())
               // .jsonPath("$.CreateAt").isEqualTo(oProduct.getCreateAt());
        //verify(oProductRepository,times(1)).findById(oProduct.getId());
    }

    @Test
    void findProducts() {
        //when(oProductRepository.findAll()).thenReturn(Flux.just(oProduct));
        //oWebTestProduct.get()
          //      .uri("/api/clients")
            //    .exchange()
              //  .expectStatus().isOk()
                //.expectBody()
       //         .jsonPath("$.Id").isEqualTo(oProduct.getId())
         //       .jsonPath("$.Name").isEqualTo(oProduct.getName())
           //     .jsonPath("$.Type").isEqualTo(oProduct.getType())
             //   .jsonPath("$.Category").isEqualTo(oProduct.getCategory())
               // .jsonPath("$.Commission").isEqualTo(oProduct.getCommission())
                //.jsonPath("$.LimitDeposit").isEqualTo(oProduct.getLimitDeposit())
       //         .jsonPath("$.LimitWithdrawal").isEqualTo(oProduct.getLimitWithdrawal())
         //       .jsonPath("$.Line").isEqualTo(oProduct.getLine())
           //     .jsonPath("$.CreateAt").isEqualTo(oProduct.getCreateAt());
        //verify(oProductRepository,times(1)).findAll();
    }

    @Test
    void updateProduct() {
    }
}