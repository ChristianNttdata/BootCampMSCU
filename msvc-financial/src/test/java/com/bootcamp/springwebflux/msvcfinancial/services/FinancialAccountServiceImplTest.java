package com.bootcamp.springwebflux.msvcfinancial.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.bootcamp.springwebflux.msvcfinancial.models.documents.Movement;
import com.bootcamp.springwebflux.msvcfinancial.repository.MovementRepository;
import com.bootcamp.springwebflux.msvcfinancial.webclient.MsvcAdministrativeWebClient;
import com.msvc.specification.api.dto.AccountDto;
import com.msvc.specification.api.dto.AdministrativeAccountDto;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class FinancialAccountServiceImplTest {

    @MockBean
    MovementRepository movementRepository;

    @MockBean
    private MsvcAdministrativeWebClient msvcAdministrativeClient;

    @Autowired
    FinancialAccountService financialAccountService;

    @Test
    void testSave_DepositTest() {

        Movement movement = new Movement();
        movement.setAmount(Double.valueOf(100));
        movement.setType("DEPOSIT");
        movement.setId("632767dfb8cc775d5e1f5743");

        AdministrativeAccountDto administrativeAccountDto = new AdministrativeAccountDto();
        administrativeAccountDto.setBalance(Double.valueOf(150));

        Mockito.when(movementRepository.save(movement))
            .thenReturn(Mono.just(movement));

        Mockito.when(msvcAdministrativeClient.getAccount("1234"))
            .thenReturn(Mono.just(administrativeAccountDto));

        Mockito.when(msvcAdministrativeClient.updateBalance("1234", administrativeAccountDto))
            .thenReturn(Mono.empty());

        Mono<AccountDto> accountDto = financialAccountService.save("1234", movement);
        StepVerifier.create(accountDto)
            .assertNext(account -> {
                assertEquals(Double.valueOf(250), Double.valueOf(account.getBalance()));
            })
            .verifyComplete();
    }

    @Test
    void testSave_ChargeTest() {

        Movement movement = new Movement();
        movement.setAmount(Double.valueOf(100));
        movement.setType("CHARGE");
        movement.setId("632767dfb8cc775d5e1f5743");

        AdministrativeAccountDto administrativeAccountDto = new AdministrativeAccountDto();
        administrativeAccountDto.setBalance(Double.valueOf(150));

        Mockito.when(movementRepository.save(movement))
            .thenReturn(Mono.just(movement));

        Mockito.when(msvcAdministrativeClient.getAccount("1234"))
            .thenReturn(Mono.just(administrativeAccountDto));

        Mockito.when(msvcAdministrativeClient.updateBalance("1234", administrativeAccountDto))
            .thenReturn(Mono.empty());

        Mono<AccountDto> accountDto = financialAccountService.save("1234", movement);
        StepVerifier.create(accountDto)
            .assertNext(account -> {
                assertEquals(Double.valueOf(50), Double.valueOf(account.getBalance()));
            })
            .verifyComplete();
    }

    @Test
    void testSave_ChargeTest_FoundsInsuficients() {

        Movement movement = new Movement();
        movement.setAmount(Double.valueOf(100));
        movement.setType("CHARGE");
        movement.setId("632767dfb8cc775d5e1f5743");

        AdministrativeAccountDto administrativeAccountDto = new AdministrativeAccountDto();
        administrativeAccountDto.setBalance(Double.valueOf(50));

        Mockito.when(movementRepository.save(movement))
            .thenReturn(Mono.just(movement));

        Mockito.when(msvcAdministrativeClient.getAccount("1234"))
            .thenReturn(Mono.just(administrativeAccountDto));

        Mockito.when(msvcAdministrativeClient.updateBalance("1234", administrativeAccountDto))
            .thenReturn(Mono.empty());

        Mono<AccountDto> accountDto = financialAccountService.save("1234", movement);
        StepVerifier.create(accountDto)
            .expectErrorMessage("No tiene saldo disponible para el retiro");
    }

    @Test
    void testValidate() {

    }
}
