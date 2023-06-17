package com.craftsoft.main.service;

import com.craftsoft.main.db.model.TransactionHistory;
import com.craftsoft.main.db.model.Wallet;
import com.craftsoft.main.db.repository.TransactionHistoryRepository;
import com.craftsoft.main.db.repository.WalletRepository;
import com.craftsoft.main.dto.WalletDto;
import com.craftsoft.main.dto.mapper.WalletMapper;
import com.craftsoft.main.dto.request.DepositRequest;
import com.craftsoft.main.dto.request.WalletRequest;
import com.craftsoft.main.dto.request.WithdrawRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletService {

    static Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    static int MAX_LIMIT_TRANSACTION = 2000;
    static int MAX_DAILY_WITHDRAW_MONEY = 5000;
    static int MAX_SUSPICIOUS_TRANSACTION = 10000;
    static String DEPOSIT_OPERATION = "DEPOSIT_OPERATION";
    static String WITHDRAW_OPERATION = "WITHDRAW_OPERATION";

    WalletRepository walletRepository;
    TransactionHistoryRepository transactionHistoryRepository;

    WalletMapper walletMapper;

    @Transactional
    public WalletDto createWallet(final WalletRequest walletRequest,
                                  final Long userId) {

        Wallet wallet = walletMapper.toWallet(walletRequest, userId);

        Wallet result = walletRepository.save(wallet);

        WalletDto walletDto = walletMapper.toWalletDto(result);

        LOGGER.info("Wallet was created by userId {} and currency {}",
                userId, wallet.getCurrency());

        return walletDto;
    }

    @Transactional
    public void depositFunds(final DepositRequest depositRequest,
                             final Long walletId,
                             final Long userId) {

        maxLimitTransaction(userId, DEPOSIT_OPERATION, depositRequest.amount());

        Wallet wallet = walletRepository.findByIdAndUserId(walletId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Not correct walletId"));

        checkIsBlock(wallet);

        walletRepository.save(wallet.setBalance(wallet.getBalance() + depositRequest.amount()));

        addTransactionHistory(wallet, DEPOSIT_OPERATION, (-1) * depositRequest.amount());

        LOGGER.info("Deposit process executed successfully by userId {} and amount {}",
                userId, depositRequest.amount());
    }

    @Transactional
    public void withdrawFunds(final WithdrawRequest withdrawRequest,
                              final Long walletId,
                              final Long userId) {

        maxLimitTransaction(userId, WITHDRAW_OPERATION, withdrawRequest.amount());

        Wallet wallet = walletRepository.findByIdAndUserId(walletId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Not correct walletId"));

        checkIsBlock(wallet);

        if (wallet.getMaxLimit() + withdrawRequest.amount() > MAX_DAILY_WITHDRAW_MONEY) {
            LOGGER.info("User {} exceeds daily limit, {}", userId, withdrawRequest.amount());
            throw new IllegalArgumentException("Limit was exceeded");
        }

        walletRepository.save(wallet.setBalance(wallet.getBalance() - withdrawRequest.amount()));

        addTransactionHistory(wallet, WITHDRAW_OPERATION, withdrawRequest.amount());

        LOGGER.info("Withdraw process executed successfully by userId {} and amount {}",
                userId, withdrawRequest.amount());
    }

    /*
     * TODO
     * in future
     * */
    public void getWalletById(final Long walletId, final Long userId) {
    }

    private void checkIsBlock(Wallet wallet) {
        if (wallet.getIsBlock()) {
            LOGGER.info("Wallet {} was blocked", wallet.getUserId());
            throw new IllegalArgumentException("Your wallet was blocked");
        }
    }

    private void maxLimitTransaction(final Long userId,
                                     final String operation,
                                     final Double amount) {
        if (amount > MAX_LIMIT_TRANSACTION) {
            LOGGER.info("User {} exceeds {} once limit, {}", userId, operation, amount);
            throw new IllegalArgumentException("Amount exceeds the limit");
        }
    }

    private void addTransactionHistory(final Wallet wallet,
                                       final String operation,
                                       final Double amount) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAmount(Math.abs(amount));
        transactionHistory.setWalletId(wallet.getId());
        transactionHistory.setUserId(wallet.getUserId());
        transactionHistory.setOperation(operation);
        transactionHistory.setIsSuspicious(wallet.getIsSuspicious());
        transactionHistory.setBalanceBefore(wallet.getBalance() + amount);
        transactionHistory.setBalanceAfter(wallet.getBalance());
        if (Math.abs(amount) > MAX_SUSPICIOUS_TRANSACTION) {
            transactionHistory.setIsSuspicious(true);
        }
        transactionHistoryRepository.save(transactionHistory);
    }
}
