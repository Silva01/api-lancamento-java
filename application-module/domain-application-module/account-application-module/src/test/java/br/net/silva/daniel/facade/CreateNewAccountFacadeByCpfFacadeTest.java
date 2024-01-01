package br.net.silva.daniel.facade;

import br.net.silva.business.dto.CreateNewAccountByCpfDTO;
import br.net.silva.business.facade.CreateNewAccountByCpfFacade;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.repository.Repository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class CreateNewAccountFacadeByCpfFacadeTest {

    @Mock
    private Repository<Boolean> findIsExistsPeerCPFRepository;

    @Mock
    private Repository<Account> saveRepository;

    @BeforeEach
    void configSetup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mustCreateNewAccountByCpf() throws GenericException {
        when(findIsExistsPeerCPFRepository.exec(Mockito.anyString())).thenReturn(false);
        when(saveRepository.exec(Mockito.any(Account.class))).thenReturn(build());

        var createNewAccountByCpfFacade = new CreateNewAccountByCpfFacade(findIsExistsPeerCPFRepository, saveRepository);
        var accountDTO = createNewAccountByCpfFacade.execute(new CreateNewAccountByCpfDTO("11122233344", 4567));

        assertNotNull(accountDTO);
        assertEquals(4567, accountDTO.bankAgencyNumber());
        assertEquals("11122233344", accountDTO.cpf());
    }

    @Test
    void mustNotCreateNewAccountByCpf() {
        when(findIsExistsPeerCPFRepository.exec(Mockito.anyString())).thenReturn(true);

        var createNewAccountByCpfFacade = new CreateNewAccountByCpfFacade(findIsExistsPeerCPFRepository, saveRepository);
        Assertions.assertThrows(GenericException.class, () -> createNewAccountByCpfFacade.execute(new CreateNewAccountByCpfDTO("11122233344", 4567)));
    }

    @Test
    void mustNotCreateNewAccountByCpfWithUnknownError() {
        when(findIsExistsPeerCPFRepository.exec(Mockito.anyString())).thenReturn(true);
        when(saveRepository.exec(Mockito.any(Account.class))).thenThrow(new RuntimeException("Unknown error"));

        var createNewAccountByCpfFacade = new CreateNewAccountByCpfFacade(findIsExistsPeerCPFRepository, saveRepository);
        Assertions.assertThrows(GenericException.class, () -> createNewAccountByCpfFacade.execute(new CreateNewAccountByCpfDTO("11122233344", 4567)));
    }

    private Account build() {
        return new Account(1234, 4567, BigDecimal.valueOf(1000), "123456", true, "11122233344", null, new ArrayList<>());
    }

}