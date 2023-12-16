package br.net.silva.daniel.facade;

import br.net.silva.daniel.dto.AddressRequestDTO;
import br.net.silva.daniel.dto.ClientRequestDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Address;
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

class CreateNewClientAndAccountFacadeTest {

    @Mock
    private Repository<Boolean> findIsExistsPeerCPFRepository;

    @Mock
    private Repository<Account> accountSaveRepository;

    @Mock
    private Repository<Client> clientSaveRepository;

    @BeforeEach
    void configSetup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mustCreateNewClientAndAccountWithSuccess() throws GenericException {
        when(findIsExistsPeerCPFRepository.exec(Mockito.anyString())).thenReturn(false);
        when(accountSaveRepository.exec(Mockito.any(Account.class))).thenReturn(buildAccount());
        when(clientSaveRepository.exec(Mockito.any(Client.class))).thenReturn(buildClient());

        var createNewClientAndAccountFacade = new CreateNewClientAndAccountFacade(findIsExistsPeerCPFRepository, accountSaveRepository, clientSaveRepository);
        var accountDTO = createNewClientAndAccountFacade.execute(buildClientDTO());

        assertNotNull(accountDTO);
        assertEquals(4567, accountDTO.bankAgencyNumber());
        assertEquals("11122233344", accountDTO.cpf());
    }

    private ClientRequestDTO buildClientDTO() {
        var address = new AddressRequestDTO("street", "1234", "complement", "neighborhood", "state", "city", "00000-00");
        return new ClientRequestDTO(null, "11122233344", "Daniel", "61999992222", true, 1234, address);
    }

    private Client buildClient() {
        var address = new Address("street", "1234", "complement", "neighborhood", "state", "city", "00000-00");
        return new Client("1", "11122233344", "Daniel", "61999992222", true, address);
    }

    private Account buildAccount() {
        return new Account(1234, 4567, BigDecimal.valueOf(1000), "123456", true, "11122233344", null, new ArrayList<>());
    }

}