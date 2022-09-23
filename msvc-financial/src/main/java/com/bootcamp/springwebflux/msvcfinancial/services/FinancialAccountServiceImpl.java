package com.bootcamp.springwebflux.msvcfinancial.services;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootcamp.springwebflux.msvcfinancial.models.documents.Movement;
import com.bootcamp.springwebflux.msvcfinancial.repository.MovementRepository;
import com.bootcamp.springwebflux.msvcfinancial.webclient.MsvcAdministrativeWebClient;
import com.msvc.specification.api.dto.AccountDto;
import com.msvc.specification.api.dto.AdministrativeAccountDto;

import reactor.core.publisher.Mono;

/**
 * FinancialAccountService.
 */
@Service
public class FinancialAccountServiceImpl implements FinancialAccountService {

    /**
     * Logger.
     */
    Logger logger = LoggerFactory.getLogger(FinancialAccountServiceImpl.class);

    /**
     * MovementRepository.
     */
    @Autowired
    private MovementRepository movementRepository;

    /**
     * MsvcAdministrativeWebClient.
     */
    @Autowired
    private MsvcAdministrativeWebClient msvcAdministrativeClient;

    /**
     * This method save Movement.
     * @return AccountDto
     */
    @Override
    public Mono<AccountDto> save(final String id, final Movement movement) {
        logger.info("save(Movements getAccountId): " + id);
        movement.setAccountId(id);
        movement.setCreateAt(new Date());
        AdministrativeAccountDto account = new AdministrativeAccountDto();
        return Mono.just(movement)
            .flatMap(x-> msvcAdministrativeClient.getAccount(id))
            .flatMap(a -> validate(a, movement)
                .flatMap(ac -> movementRepository.save(movement))
                .flatMap(c-> 
                    {
                        a.setBalance(a.getBalance() == null ? 0 : a.getBalance());
                        if (movement.getType().equals("DEPOSIT")) {
                            account.setBalance(a.getBalance() + movement.getAmount());
                        } else {
                            account.setBalance(a.getBalance() - movement.getAmount());
                        }
                        a.setBalance(account.getBalance());
                        return msvcAdministrativeClient.updateBalance(id, account).then(Mono.just(a));
                    }
                ).map(accountMap -> {
                    AccountDto accountDto = new AccountDto();
                    accountDto.setBalance(accountMap.getBalance());
                    return accountDto;
                })
            );
    }

    /**
     * This method validatevalidate AdministrativeAccountDto.
     * @return AdministrativeAccountDto
     */
    public Mono<AdministrativeAccountDto> validate(final AdministrativeAccountDto administrativeAccountDto,
            final Movement movement) {
        return (administrativeAccountDto.getBalance() < movement.getAmount()
                &&
                !movement.getType().equals("DEPOSIT")) ?
                    Mono.error(() -> new RuntimeException("No tiene saldo disponible para el retiro"))
                :
                    Mono.just(administrativeAccountDto);
    }
}
