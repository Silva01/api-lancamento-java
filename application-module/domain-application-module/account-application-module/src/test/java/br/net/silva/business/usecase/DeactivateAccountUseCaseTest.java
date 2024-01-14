package br.net.silva.business.usecase;

import br.net.silva.business.dto.FindAccountDTO;
import br.net.silva.business.mapper.MapToAccountMapper;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.utils.ConverterUtils;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DeactivateAccountUseCaseTest {

    private DeactivateAccountUseCase deactivateAccountUseCase;

    private MapToAccountMapper mapper;

    private CreateAccountByAccountDTOFactory factory;

    @Mock
    private Repository<Account> deactivateAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deactivateAccountUseCase = new DeactivateAccountUseCase(deactivateAccountRepository);
        this.mapper = MapToAccountMapper.INSTANCE;
        this.factory = new CreateAccountByAccountDTOFactory();
    }

    @Test
    void mustDeactivateAccountWithSuccess() throws GenericException {
        when(deactivateAccountRepository.exec(Mockito.any(String.class))).thenReturn(buildMockAccount(false));

        var findAccountDTO = new FindAccountDTO("99988877766", 0, 0, null);
        var source = new Source(new HashMap<>(), ConverterUtils.convertJsonToInputMap(ConverterUtils.convertObjectToJson(findAccountDTO)));
        deactivateAccountUseCase.exec(source);

        var account = factory.create(mapper.mapToAccountDTO(source));
        assertNotNull(account);
        Mockito.verify(deactivateAccountRepository, Mockito.times(1)).exec(findAccountDTO.cpf());

        var accountDTO = account.build();
        assertFalse(accountDTO.active());
    }

    @Test
    void mustDeactivateAccountWithErrorAnyone() {
        when(deactivateAccountRepository.exec(Mockito.any(String.class))).thenReturn(buildMockAccount(false));
        assertThrows(GenericException.class, () -> deactivateAccountUseCase.exec(null));
    }

    private Account buildMockAccount(boolean active) {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, Collections.emptyList());
    }

}