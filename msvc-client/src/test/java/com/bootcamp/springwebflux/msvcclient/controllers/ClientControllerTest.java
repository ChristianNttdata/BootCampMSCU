package com.bootcamp.springwebflux.msvcclient.controllers;

import com.bootcamp.springwebflux.msvcclient.models.documents.Client;
import com.bootcamp.springwebflux.msvcclient.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientControllerTest {
    @Mock
    private ClientRepository oClientRepository;

    private WebTestClient oWebTestClient;
    private Client oClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        oClient = new Client();
        oClient.setId(new String());
        oClient.setFirstName("Primer Nombre");
        oClient.setLastName("Primer Apellido");
        oClient.setType("Tipo test");
    }

    @Test
    void addClient() {
        when(oClientRepository.save(any(Client.class))).thenReturn(Mono.just(oClient));
        oWebTestClient.post()
                .uri("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(oClient))
                .exchange()
                .expectStatus().isCreated();
        verify(oClientRepository,times(1)).save(oClient);
    }

    @Test
    void deleteClient() {
    }

    @Test
    void findClientById() {
        when(oClientRepository.findAll()).thenReturn(Flux.just(oClient));
        oWebTestClient.get()
                .uri("/api/clients")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.Id").isNotEmpty()
                .jsonPath("$.Id").isEqualTo(oClient.getId())
                .jsonPath("$.FirstName").isEqualTo(oClient.getFirstName())
                .jsonPath("$.LastName").isEqualTo(oClient.getLastName())
                .jsonPath("$.Type").isEqualTo(oClient.getType());
        verify(oClientRepository,times(1)).findById(oClient.getId());
    }

    @Test
    void findClients() {
        when(oClientRepository.findAll()).thenReturn(Flux.just(oClient));
        oWebTestClient.get()
                .uri("/api/clients")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.Id").isNotEmpty()
                .jsonPath("$.FirstName").isNotEmpty()
                .jsonPath("$.LastName").isNotEmpty()
                .jsonPath("$.Type").isNotEmpty()
                .jsonPath("$.Id").isEqualTo(oClient.getId())
                .jsonPath("$.FirstName").isEqualTo(oClient.getFirstName())
                .jsonPath("$.LastName").isEqualTo(oClient.getLastName())
                .jsonPath("$.Type").isEqualTo(oClient.getType());
        verify(oClientRepository,times(1)).findAll();
    }

    @Test
    void updateClient() {
    }
}