package com.craftsoft.main.unittest;

import com.craftsoft.main.db.model.TransactionHistory;
import com.craftsoft.main.db.model.Wallet;
import com.craftsoft.main.db.repository.TransactionHistoryRepository;
import com.craftsoft.main.db.repository.WalletRepository;
import com.craftsoft.main.dto.WalletDto;
import com.craftsoft.main.dto.mapper.WalletMapper;
import com.craftsoft.main.dto.request.DepositRequest;
import com.craftsoft.main.dto.request.WalletRequest;
import com.craftsoft.main.dto.request.WithdrawRequest;
import com.craftsoft.main.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
class WalletTest {

    @Mock
    private WalletRepository walletRepository;
    @Mock
    private WalletMapper walletMapper;
    @InjectMocks
    private WalletService walletService;
    @Mock
    private TransactionHistoryRepository transactionHistoryRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this); // Инициализирует поля с аннотацией @Mock
    }

    @Test
    void createWallet() {
        Long userId = 1L;

        WalletRequest walletRequest = getWalletRequest();

        Wallet wallet = getWallet();

        Wallet walletSaved = getWallet();
        walletSaved.setId(1L);

        WalletDto walletDto = new WalletDto(1L, 1L, 0.0, "EUR");

        when(walletMapper.toWallet(walletRequest, userId)).thenReturn(wallet);
        when(walletRepository.save(wallet)).thenReturn(walletSaved);
        when(walletMapper.toWalletDto(walletSaved)).thenReturn(walletDto);

        WalletDto result = walletService.createWallet(walletRequest, userId);

        assertEquals(walletDto, result);

        verify(walletMapper).toWallet(walletRequest, userId);
        verify(walletRepository).save(wallet);
        verify(walletMapper).toWalletDto(walletSaved);
    }

    @Test
    public void testCreateWalletFailed() {
        Long userId = 1L;

        WalletRequest walletRequest = getWalletRequest();

        when(walletMapper.toWallet(walletRequest, userId)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> walletService.createWallet(walletRequest, userId));
    }

    @Test
    public void testDepositFunds() {
        DepositRequest depositRequest = new DepositRequest(1000.0);
        Long walletId = 1L;
        Long userId = 1L;
        Wallet wallet = new Wallet();
        wallet.setBalance(2000.0);

        when(walletRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(wallet));

        walletService.depositFunds(depositRequest, walletId, userId);

        verify(walletRepository, times(1)).findByIdAndUserId(anyLong(), anyLong());
        verify(walletRepository, times(1)).save(any(Wallet.class));
        verify(transactionHistoryRepository, times(1)).save(any(TransactionHistory.class));
    }

    @Test
    public void testDepositFundsFailed() {
        DepositRequest depositRequest = new DepositRequest(2015.0);
        Long walletId = 1L;
        Long userId = 1L;
        Wallet wallet = new Wallet();
        wallet.setBalance(2000.0);

        assertThrows(RuntimeException.class, () -> walletService.depositFunds(depositRequest, walletId, userId));
    }

    @Test
    public void testWithdrawFunds() {
        WithdrawRequest withdrawRequest = new WithdrawRequest(500.0);
        Long walletId = 1L;
        Long userId = 1L;
        Wallet wallet = new Wallet();
        wallet.setBalance(2000.0);
        wallet.setMaxLimit(3452.0);

        when(walletRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(wallet));

        walletService.withdrawFunds(withdrawRequest, walletId, userId);

        verify(walletRepository, times(1)).findByIdAndUserId(anyLong(), anyLong());
        verify(walletRepository, times(1)).save(any(Wallet.class));
        verify(transactionHistoryRepository, times(1)).save(any(TransactionHistory.class));
    }

    @Test
    public void testWithdrawFundsFailed() {
        WithdrawRequest withdrawRequest = new WithdrawRequest(500.0);
        Long walletId = 1L;
        Long userId = 1L;
        Wallet wallet = new Wallet();
        wallet.setBalance(2000.0);
        wallet.setMaxLimit(3452.0);

        assertThrows(RuntimeException.class, () -> walletService.withdrawFunds(withdrawRequest, walletId, userId));
    }

    @Test
    void getWalletById() {
    }

    private Wallet getWallet() {
        Wallet wallet = new Wallet();
        wallet.setFirstName("TestName");
        wallet.setLastName("TestLastName");
        wallet.setEmail("test@gmail.com");
        wallet.setPhoneNumber("123456789");
        wallet.setDateOfBirth(new Timestamp(1000));
        wallet.setAddress("Test city");
        return wallet;
    }

    private WalletRequest getWalletRequest() {
        return new WalletRequest("TestName",
                "TestLastName",
                "test@gmail.com",
                "123456789",
                new Timestamp(1000),
                "Test city");
    }
}