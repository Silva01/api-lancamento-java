package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.CreateNewAccountByCpfDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.AccountExistsForCPFInformatedException;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateNewAccountFacadePeerCPFUseCaseTest {
    @Mock
    private Repository<Boolean> findIsExistsPeerCPFRepository;

    @Mock
    private Repository<Account> saveRepository;

    private CreateNewAccountPeerCPFUseCase useCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new CreateNewAccountPeerCPFUseCase(findIsExistsPeerCPFRepository, saveRepository);
    }

    @Test
    public void testExec_accountDoesNotExist_createsNewAccount() throws GenericException {
        // Arrange
        String cpf = "12345678901";
        CreateNewAccountByCpfDTO dto = new CreateNewAccountByCpfDTO(cpf, 1234);
        Mockito.when(findIsExistsPeerCPFRepository.exec(cpf)).thenReturn(false);
        Mockito.when(saveRepository.exec(ArgumentMatchers.any(Account.class))).thenReturn(buildMockAccount());

        // Act
        AccountDTO result = useCase.exec(dto);

        // Assert
        assertNotNull(result);
        Mockito.verify(saveRepository, Mockito.times(1)).exec(ArgumentMatchers.any(Account.class));
    }

    @Test
    public void testExec_accountExists_throwsException() {
        // Arrange
        String cpf = "12345678901";
        CreateNewAccountByCpfDTO dto = new CreateNewAccountByCpfDTO(cpf, 1234);
        Mockito.when(findIsExistsPeerCPFRepository.exec(cpf)).thenReturn(true);

        // Act and Assert
        assertThrows(AccountExistsForCPFInformatedException.class, () -> useCase.exec(dto));
    }

    private Account buildMockAccount() {
        return new Account(1234, "1234567", "12345678901");
    }
}